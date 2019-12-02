package com.leimingtech.cms.net.content;

import java.util.ArrayList;

/**
 * 权重计算
 * 
 * @author 谢进伟
 * @DateTime 2015-7-30 上午1:10:32
 */
public class GaussSmooth {
	
	/**
	 * 生成正态分布的矩阵
	 * 
	 * @param r
	 *            模糊半径
	 * @return
	 */
	public static double [] getGaussWindow(int r) {
		int size = 1 + r * 2;
		double [] window = new double [size];
		for(int i = -r ; i <= r ; i++) {
			window[i + r] = 1.0 / Math.pow(Math.E , (i * i + 0.0) / (2 * r * r));
			
		}
		return window;
	}
	
	/**
	 * 计算制定数据源中各个点对应的权重值
	 * 
	 * @param list
	 *            数据源
	 * @param r
	 *            模糊半径
	 * @return 数据源个点对应的权重值
	 */
	public static ArrayList<Double> gaussSmooth(ArrayList<Double> list , int r) {
		double [] window = getGaussWindow(r);
		double wSum = 0;
		for(double d : window) {
			wSum += d;
		}
		ArrayList<Double> result = new ArrayList<Double>();
		for(int i = 0 ; i < list.size() ; i++) {
			if(i < r || i > list.size() - 1 - r) {
				result.add(list.get(i));
				continue;
			}
			double sum = 0;
			for(int j = -r ; j <= r ; j++) {
				int index = i + j;
				sum += window[j + r] * list.get(index);
			}
			double value = sum * window[r] / wSum;
			result.add(value);
			
		}
		return result;
		
	}
	
	public static void main(String [] args) {
		ArrayList<Double> doubles = new ArrayList<Double>();
		doubles.add(1.0);
		doubles.add(0.2);
		doubles.add(0.1);
		doubles.add(0.3);
		doubles.add(1.0);
		System.out.println(gaussSmooth(doubles , 5));
	}
}
