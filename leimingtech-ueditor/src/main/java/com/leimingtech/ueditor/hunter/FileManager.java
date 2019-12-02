package com.leimingtech.ueditor.hunter;

import java.io.File;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.leimingtech.core.base.SortDirection;
import com.leimingtech.core.entity.AttachPictureEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.hibernate.qbc.PageList;
import com.leimingtech.core.service.AttachPictureServiceI;
import com.leimingtech.core.util.PathFormat;
import com.leimingtech.core.util.PlatFormUtil;
import com.leimingtech.ueditor.define.ActionMap;
import com.leimingtech.ueditor.define.AppInfo;
import com.leimingtech.ueditor.define.BaseState;
import com.leimingtech.ueditor.define.MultiState;
import com.leimingtech.ueditor.define.State;

public class FileManager {

	private String dir = null;
	private String rootPath = null;
	private String[] allowFiles = null;
	private int count = 0;
	private Integer upload_file_type=null;
	
	public FileManager ( Map<String, Object> conf ) {

		this.rootPath = (String)conf.get( "rootPath" );
		this.dir = this.rootPath + (String)conf.get( "dir" );
		this.allowFiles = this.getAllowFiles( conf.get("allowFiles") );
		this.count = (Integer)conf.get( "count" );
		this.upload_file_type=(Integer)conf.get("upload_file_type");
	}
	
	public State listFile ( int index ) {
		State state = null;
		
		switch (upload_file_type) {
		case ActionMap.LIST_IMAGE:
			state = getImageFile(index);
			break;
		case ActionMap.LIST_FILE:
			state = getFiles(index);
			break;
		}
		return state;
		
	}
	
	private State getImageFile(int index){
		State state = null;
		AttachPictureServiceI attachPictureService=(AttachPictureServiceI)PlatFormUtil.getInterface("attachPictureService");
		CriteriaQuery cq = new CriteriaQuery(AttachPictureEntity.class, this.count, index+1, "", "");
		cq.eq("siteid", PlatFormUtil.getSessionSiteId());
		cq.addOrder("createdate", SortDirection.desc);
		cq.add();
		PageList pageList = attachPictureService.getPageList(cq, true);
		List<AttachPictureEntity> list = pageList.getResultList();
		
		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
			
			MultiState multiState = new MultiState( true );
			BaseState fileState = null;
			for ( AttachPictureEntity obj : list ) {
				if ( obj == null ) {
					break;
				}
				fileState = new BaseState( true );
				fileState.putInfo( "url", obj.getLocalpath() );
				multiState.addState( fileState );
			}
			state = multiState;
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		return state;
	}

	private State getFiles(int index) {
		File dir = new File( this.dir );
		State state = null;

		if ( !dir.exists() ) {
			return new BaseState( false, AppInfo.NOT_EXIST );
		}
		
		if ( !dir.isDirectory() ) {
			return new BaseState( false, AppInfo.NOT_DIRECTORY );
		}
		
		Collection<File> list = FileUtils.listFiles( dir, this.allowFiles, true );
		
		if ( index < 0 || index > list.size() ) {
			state = new MultiState( true );
		} else {
			Object[] fileList = Arrays.copyOfRange( list.toArray(), index, index + this.count );
			state = this.getState( fileList );
		}
		
		state.putInfo( "start", index );
		state.putInfo( "total", list.size() );
		return state;
	}
	
	private State getState ( Object[] files ) {
		
		MultiState state = new MultiState( true );
		BaseState fileState = null;
		
		File file = null;
		
		for ( Object obj : files ) {
			if ( obj == null ) {
				break;
			}
			file = (File)obj;
			fileState = new BaseState( true );
			fileState.putInfo( "url", PathFormat.format( this.getPath( file ) ) );
			state.addState( fileState );
		}
		
		return state;
		
	}
	
	private String getPath ( File file ) {
		
		String path = file.getAbsolutePath();
		path = PathFormat.format(path);
		return path.replace( this.rootPath, "/" );
		
	}
	
	private String[] getAllowFiles ( Object fileExt ) {
		
		String[] exts = null;
		String ext = null;
		
		if ( fileExt == null ) {
			return new String[ 0 ];
		}
		
		exts = (String[])fileExt;
		
		for ( int i = 0, len = exts.length; i < len; i++ ) {
			
			ext = exts[ i ];
			exts[ i ] = ext.replace( ".", "" );
			
		}
		
		return exts;
		
	}
	
}
