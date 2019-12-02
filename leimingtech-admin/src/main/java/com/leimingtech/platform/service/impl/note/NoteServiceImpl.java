package com.leimingtech.platform.service.impl.note;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.leimingtech.core.service.impl.CommonServiceImpl;
import com.leimingtech.platform.service.note.NoteServiceI;

@Service("noteService")
@Transactional
public class NoteServiceImpl extends CommonServiceImpl implements NoteServiceI {
	
}