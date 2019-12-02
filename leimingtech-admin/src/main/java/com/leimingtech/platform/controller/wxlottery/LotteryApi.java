package com.leimingtech.platform.controller.wxlottery;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.leimingtech.core.entity.member.MemberEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.leimingtech.core.controller.BaseController;
import com.leimingtech.core.entity.LotteryEntity;
import com.leimingtech.core.hibernate.qbc.CriteriaQuery;
import com.leimingtech.core.service.WxLotteryServiceI;
import com.leimingtech.platform.entity.wxlotterrecord.LotterRecordEntity;

/**
 * 互动游戏API
 * 
 * @author liuzhen
 * 
 */
@Controller
@RequestMapping("/front/lotteryApi")
public class LotteryApi extends BaseController {

	@Autowired
	private WxLotteryServiceI wxLotteryService;

	/**
	 * 砸金蛋
	 * 
	 * @return
	 */
	@RequestMapping(value = "/zajindan")
	public ModelAndView zajindan(HttpServletRequest request) {
		String lid = request.getParameter("id");// 游戏id
		String uid = request.getParameter("uid");// 会员id

		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(lid) && StringUtils.isNumeric(lid) && StringUtils.isNotEmpty(uid)) {

			LotteryEntity lottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(lid));
			MemberEntity member = wxLotteryService.getEntity(MemberEntity.class, String.valueOf(uid));
			// 开始玩游戏必须会员存在，游戏存在
			if (lottery != null && member != null) {
				boolean isOpen = false;// 游戏是否是开启状态
				boolean isTimeUp = true;//时间是否已经到
				int usenums = 0;// 用户参与次数
				String msg = "";
				String isZhongJiang = "";
				boolean islottery = false;

				Long curtime = new Date().getTime();
				if (lottery.getStatus() == 1 && lottery.getStatdate() != null && lottery.getEnddate() != null) {

					if (lottery.getEnddate().getTime() > curtime) {
						if(lottery.getStatdate().getTime() < curtime){
							isOpen = true;
							// 当前为开启状态
						}else{
							isTimeUp=false;
						}
					}
				}
				if (isOpen) {

					int maxCunYu = lottery.getAllpeople() * lottery.getCanrqnums();// 预估总参与人数

					if (StringUtils.isEmpty(isZhongJiang)) {
						CriteriaQuery cq = new CriteriaQuery(LotterRecordEntity.class);
						cq.eq("lid", Integer.valueOf(lid));
						cq.eq("wechaId", uid);
						cq.add();
						List<LotterRecordEntity> LotterRecordList = wxLotteryService.getListByCriteriaQuery(cq, false);

						if (LotterRecordList != null && LotterRecordList.size() > 0) {
							LotterRecordEntity lotterRecord = LotterRecordList.get(0);
							if (lotterRecord.getIslottery() == 1) {
								// 已经中奖
								msg = "您已经中过 <span style='color:red;'>"+lotterRecord.getPrize()+"</span> 奖了";
								isZhongJiang = "0";
								islottery = true;
								m.put("lotterRecord", lotterRecord);
								usenums=lottery.getCanrqnums();
							} else {
								int maxNum = lottery.getCanrqnums();
								if (lotterRecord.getUsenums() >= maxNum) {
									// 超出抽奖次数
									msg = "抽奖次数已用完";
									isZhongJiang = "0";
									usenums = maxNum;
								} else {
									usenums = lotterRecord.getUsenums();
									lotterRecord.setUsenums((usenums + 1));
									wxLotteryService.saveOrUpdate(lotterRecord);
								}
							}
						} else {
							LotterRecordEntity lotterRecord = new LotterRecordEntity();
							lotterRecord.setLid(String.valueOf(lid));
							lotterRecord.setWechaId(uid);
							lotterRecord.setUsenums(1);
							lotterRecord.setIslottery(0);
							wxLotteryService.save(lotterRecord);
						}
					}

					if (StringUtils.isEmpty(isZhongJiang)) {
						String sql = "select count(l.usenums) as countUserNums from tp_lottery_record as l where l.lid = ?";
						Map<String, Object> info = wxLotteryService.findOneForJdbc(sql, lottery.getId());
						int countUserNums = 0;
						if (info != null) {
							countUserNums = Integer.valueOf(info.get("countUserNums").toString());
						}

						if (countUserNums - 1 >= maxCunYu) {
							// 当活动实际参与总人数=预估人数时 将不再发放奖品 都是谢谢参与
							isZhongJiang = "0";
							msg = "谢谢参与";
						}
					}

					if (StringUtils.isEmpty(isZhongJiang)) {
						// 中奖概率 = 奖品总数/(预估活动人数*每人抽奖次数)
						int maxJiangPin = lottery.getFistnums() + lottery.getFivenums() + lottery.getFourlucknums()
								+ lottery.getSecondnums() + lottery.getSixnums() + lottery.getThirdnums();// 奖品总数
						double gailv = maxJiangPin / maxCunYu;// 概率

						int random = (int) (1 / gailv);
						Random r = new Random();
						if (gailv >= 1 || r.nextInt(100) < random - 1) {
							//当概率大于1直接中奖 ，另外通过概率产生随机数，产生0-99的区间数
							
							// 中奖
							StringBuilder sb = new StringBuilder();
							if (lottery.getFistnums() != 0) {
								if (lottery.getFistlucknums() < lottery.getFistnums()) {
									sb.append("1");
								}
								if (lottery.getSecondnums() != 0) {
									if (lottery.getSecondlucknums() < lottery.getSecondnums()) {
										sb.append("2");
									}
									if (lottery.getThirdnums() != 0) {
										if (lottery.getThirdlucknums() < lottery.getThirdnums()) {
											sb.append("3");
										}
										if (lottery.getFournums() != 0) {
											if (lottery.getFourlucknums() < lottery.getFournums()) {
												sb.append("4");
											}
											if (lottery.getFivenums() != 0) {
												if (lottery.getFivelucknums() < lottery.getFivenums()) {
													sb.append("5");
												}
												if (lottery.getSixnums() != 0) {
													if (lottery.getSixlucknums() < lottery.getSixnums()) {
														sb.append("6");
													}
												}
											}
										}
									}
								}
							}
							if (sb.toString().length() > 0) {
								int winNum = Integer.valueOf(RandomStringUtils.random(1, sb.toString()));
								switch (winNum) {
								case 1:
									if (lottery.getFistnums() > lottery.getFistlucknums()) {
										isZhongJiang = "1";
										lottery.setFistlucknums(lottery.getFistlucknums() + 1);
										msg = "一等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 2:
									if (lottery.getSecondnums() > lottery.getSecondlucknums()) {
										isZhongJiang = "1";
										lottery.setSecondlucknums(lottery.getSecondlucknums() + 1);
										msg = "二等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 3:
									if (lottery.getThirdnums() > lottery.getThirdlucknums()) {
										isZhongJiang = "1";
										lottery.setThirdlucknums(lottery.getThirdlucknums() + 1);
										msg = "三等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 4:
									if (lottery.getFournums() > lottery.getFourlucknums()) {
										isZhongJiang = "1";
										lottery.setFourlucknums(lottery.getFourlucknums() + 1);
										msg = "四等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 5:
									if (lottery.getFivenums() > lottery.getFivelucknums()) {
										isZhongJiang = "1";
										lottery.setFivelucknums(lottery.getFivelucknums() + 1);
										msg = "五等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 6:
									if (lottery.getSixnums() > lottery.getSixlucknums()) {
										isZhongJiang = "1";
										lottery.setSixlucknums(lottery.getSixlucknums() + 1);
										msg = "六等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								}

								wxLotteryService.saveOrUpdate(lottery);

								if (StringUtils.isNotEmpty(isZhongJiang) && "1".equals(isZhongJiang)) {
									HttpSession session = request.getSession();
									Map<String, Object> sesessionData = new HashMap<String, Object>();
									sesessionData.put("msg", msg);
									sesessionData.put("sn", RandomStringUtils.random(13,
											"1234567890abc1234567890defghi1234567890jklmno1234567890pqrs1234567890tuvwxyz"));
									session.setAttribute("lottery_" + uid + "_" + lid, sesessionData);
								}
							} else {
								//没有匹配到奖项
								isZhongJiang = "0";
								msg = "谢谢参与";
							}

						}else{
							//中奖概率过滤后没中奖
							isZhongJiang = "0";
							msg = "谢谢参与";
						}
					}

				}

				m.put("lottery", lottery);
				m.put("msg", msg);
				m.put("islottery", islottery);
				m.put("isZhongJiang", isZhongJiang);
				m.put("usenums", usenums);
				m.put("isOpen", isOpen);
				m.put("isTimeUp", isTimeUp);
				m.put("uid", uid);
				m.put("lid", lid);
			}
		}

		return new ModelAndView("weixin/wxlottery/zajindan", m);
	}

	/**
	 * 提交中奖信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/submitZhongJiang")
	@ResponseBody
	public String submitZhongJiang(HttpServletRequest request) {
		JSONObject j = new JSONObject();

		String msg = "对不起，您提交的数据格式不正确！";
		String tel = request.getParameter("tel");
		String uid = request.getParameter("wechaid");
		String lid = request.getParameter("lid");
		boolean isSuccess = false;

		if (StringUtils.isNotEmpty(lid) && StringUtils.isNumeric(lid) && StringUtils.isNotEmpty(uid) && StringUtils.isNotEmpty(tel)
				&& tel.length() == 11) {
			LotteryEntity lottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(lid));
			MemberEntity member = wxLotteryService.getEntity(MemberEntity.class, String.valueOf(uid));

			if (lottery != null && member != null) {

				CriteriaQuery cq = new CriteriaQuery(LotterRecordEntity.class);
				cq.eq("lid", String.valueOf(lid));
				cq.eq("wechaId", uid);
				cq.add();
				List<LotterRecordEntity> lotterRecordList = wxLotteryService.getListByCriteriaQuery(cq, false);
				HttpSession session = request.getSession();
				Map<String, Object> data = (Map<String, Object>) session.getAttribute("lottery_" + uid + "_" + lid);

				if (lotterRecordList != null && lotterRecordList.size() > 0 && MapUtils.isNotEmpty(data) && data.get("msg") != null) {
					LotterRecordEntity lotterRecord = lotterRecordList.get(0);
					lotterRecord.setPrize(data.get("msg").toString());
					lotterRecord.setPhone(tel);
					lotterRecord.setTime(new Date());
					lotterRecord.setSendstutas(0);// 未发奖
					lotterRecord.setIslottery(1);// 已中奖
					String sn = data.get("sn").toString();
					lotterRecord.setSn(sn);

					wxLotteryService.saveOrUpdate(lotterRecord);
					session.removeAttribute("lottery_" + uid + "_" + lid);
					msg = "恭喜！尊敬的" + tel + "请您保持手机通畅！请您牢记的领奖号:" + sn;
					isSuccess = true;
				}

			}
		}

		j.accumulate("success", isSuccess);
		j.accumulate("msg", msg);

		return j.toString();
	}

	/**
	 * 大转盘
	 * 
	 * @return
	 */
	@RequestMapping(value = "/dazhuanpan")
	public ModelAndView dazhuanpan(HttpServletRequest request) {
		String lid = request.getParameter("id");// 游戏id
		String uid = request.getParameter("uid");// 会员id

		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(lid) && StringUtils.isNumeric(lid) && StringUtils.isNotEmpty(uid)) {

			LotteryEntity lottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(lid));
			MemberEntity member = wxLotteryService.getEntity(MemberEntity.class, String.valueOf(uid));
			// 开始玩游戏必须会员存在，游戏存在
			if (lottery != null && member != null) {
				boolean isOpen = false;// 游戏是否是开启状态
				boolean isTimeUp = true;//时间是否已经到
				int usenums = 0;// 用户参与次数
				boolean islottery = false;

				Long curtime = new Date().getTime();
				if (lottery.getStatus() == 1 && lottery.getStatdate() != null && lottery.getEnddate() != null) {

					if (lottery.getEnddate().getTime() > curtime) {
						if(lottery.getStatdate().getTime() < curtime){
							isOpen = true;
							// 当前为开启状态
						}else{
							isTimeUp=false;
						}
					}
				}
				if (isOpen) {

					int maxCunYu = lottery.getAllpeople() * lottery.getCanrqnums();// 预估总参与人数

					CriteriaQuery cq = new CriteriaQuery(LotterRecordEntity.class);
					cq.eq("lid", String.valueOf(lid));
					cq.eq("wechaId", uid);
					cq.add();
					List<LotterRecordEntity> LotterRecordList = wxLotteryService.getListByCriteriaQuery(cq, false);

					if (LotterRecordList != null && LotterRecordList.size() > 0) {
						LotterRecordEntity lotterRecord = LotterRecordList.get(0);
						if (lotterRecord.getIslottery() == 1) {
							// 已经中奖
							islottery = true;
							m.put("lotterRecord", lotterRecord);
						}
						usenums = lotterRecord.getUsenums();
					}
				}

				m.put("lottery", lottery);
				m.put("islottery", islottery);
				m.put("usenums", usenums);
				m.put("isOpen", isOpen);
				m.put("isTimeUp", isTimeUp);
				m.put("uid", uid);
				m.put("lid", lid);
			}
		}

		return new ModelAndView("weixin/wxlottery/dazhuanpan", m);
	}

	/**
	 * 大转盘抽奖
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/zhanPanChouJiang")
	@ResponseBody
	public String zhanPanChouJiang(HttpServletRequest request) {
		JSONObject j = new JSONObject();

		int norun = 0;
		int canrqnums = 1;// 每人最多抽奖次数
		boolean success = false;
		int prizetype = 0;// 中奖项
		int usenums = 0;// 目前会员总抽奖次数

		String uid = request.getParameter("oneid");// 用户id
		String lid = request.getParameter("id");// 活动id

		Map<String, Object> m = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(lid) && StringUtils.isNumeric(lid) && StringUtils.isNotEmpty(uid)) {

			LotteryEntity lottery = wxLotteryService.getEntity(LotteryEntity.class, String.valueOf(lid));
			MemberEntity member = wxLotteryService.getEntity(MemberEntity.class, String.valueOf(uid));
			// 开始玩游戏必须会员存在，游戏存在
			if (lottery != null && member != null) {
				boolean isOpen = false;// 游戏是否是开启状态
				String msg = "";
				String isZhongJiang = "";
				boolean islottery = false;

				Long curtime = new Date().getTime();
				if (lottery.getStatus() == 1 && lottery.getStatdate() != null && lottery.getStatdate().getTime() < curtime
						&& lottery.getEnddate() != null && lottery.getEnddate().getTime() > curtime) {
					isOpen = true;
					// 当前为开启状态
				}
				if (isOpen) {

					int maxCunYu = lottery.getAllpeople() * lottery.getCanrqnums();// 预估总参与人数

					if (StringUtils.isEmpty(isZhongJiang)) {
						CriteriaQuery cq = new CriteriaQuery(LotterRecordEntity.class);
						cq.eq("lid", String.valueOf(lid));
						cq.eq("wechaId", uid);
						cq.add();
						List<LotterRecordEntity> LotterRecordList = wxLotteryService.getListByCriteriaQuery(cq, false);

						if (LotterRecordList != null && LotterRecordList.size() > 0) {
							LotterRecordEntity lotterRecord = LotterRecordList.get(0);
							if (lotterRecord.getIslottery() == 1) {
								// 已经中奖
								msg = "您已经中过 <span style='color:red;'>"+lotterRecord.getPrize()+"</span> 奖了";
								isZhongJiang = "0";
								islottery = true;
								m.put("lotterRecord", lotterRecord);
								usenums = lotterRecord.getUsenums();
							}

							int maxNum = lottery.getCanrqnums();
							if (lotterRecord.getUsenums() >= maxNum) {
								// 超出抽奖次数
								msg = "抽奖次数已用完";
								isZhongJiang = "0";
								norun = 2;
							} else {
								usenums = lotterRecord.getUsenums() + 1;
								lotterRecord.setUsenums(usenums);
								wxLotteryService.saveOrUpdate(lotterRecord);
							}
						} else {
							LotterRecordEntity lotterRecord = new LotterRecordEntity();
							lotterRecord.setLid(String.valueOf(lid));
							lotterRecord.setWechaId(uid);
							lotterRecord.setUsenums(1);
							lotterRecord.setIslottery(0);
							lotterRecord.setCreatedtime(new Date());//创建时间
							wxLotteryService.save(lotterRecord);
						}
					}

					if (StringUtils.isEmpty(isZhongJiang)) {
						String sql = "select count(l.usenums) as countUserNums from tp_lottery_record as l where l.lid = ?";
						Map<String, Object> info = wxLotteryService.findOneForJdbc(sql, lottery.getId());
						int countUserNums = 0;
						if (info != null) {
							countUserNums = Integer.valueOf(info.get("countUserNums").toString());
						}

						if (countUserNums - 1 >= maxCunYu) {
							// 当活动实际参与总人数=预估人数时 将不再发放奖品 都是谢谢参与
							isZhongJiang = "0";
							msg = "谢谢参与";
						}
					}

					if (StringUtils.isEmpty(isZhongJiang)) {
						// 中奖概率 = 奖品总数/(预估活动人数*每人抽奖次数)
						int maxJiangPin = lottery.getFistnums() + lottery.getFivenums() + lottery.getFourlucknums()
								+ lottery.getSecondnums() + lottery.getSixnums() + lottery.getThirdnums();// 奖品总数
						double gailv = maxJiangPin / maxCunYu;// 概率

						int random = (int) (1 / gailv);
						Random r = new Random();
						if (gailv >= 1 || r.nextInt(100) < random - 1) {
							//当概率大于1直接中奖 ，另外通过概率产生随机数，产生0-99的区间数
							
							// 中奖
							StringBuilder sb = new StringBuilder();
							if (lottery.getFistnums() != 0) {
								if (lottery.getFistlucknums() < lottery.getFistnums()) {
									sb.append("1");
								}
								if (lottery.getSecondnums() != 0) {
									if (lottery.getSecondlucknums() < lottery.getSecondnums()) {
										sb.append("2");
									}
									if (lottery.getThirdnums() != 0) {
										if (lottery.getThirdlucknums() < lottery.getThirdnums()) {
											sb.append("3");
										}
										if (lottery.getFournums() != 0) {
											if (lottery.getFourlucknums() < lottery.getFournums()) {
												sb.append("4");
											}
											if (lottery.getFivenums() != 0) {
												if (lottery.getFivelucknums() < lottery.getFivenums()) {
													sb.append("5");
												}
												if (lottery.getSixnums() != 0) {
													if (lottery.getSixlucknums() < lottery.getSixnums()) {
														sb.append("6");
													}
												}
											}
										}
									}
								}
							}
							if (sb.toString().length() > 0) {
								int winNum = Integer.valueOf(RandomStringUtils.random(1, sb.toString()));
								switch (winNum) {
								case 1:
									if (lottery.getFistnums() > lottery.getFistlucknums()) {
										isZhongJiang = "1";
										lottery.setFistlucknums(lottery.getFistlucknums() + 1);
										msg = "一等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 2:
									if (lottery.getSecondnums() > lottery.getSecondlucknums()) {
										isZhongJiang = "1";
										lottery.setSecondlucknums(lottery.getSecondlucknums() + 1);
										msg = "二等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 3:
									if (lottery.getThirdnums() > lottery.getThirdlucknums()) {
										isZhongJiang = "1";
										lottery.setThirdlucknums(lottery.getThirdlucknums() + 1);
										msg = "三等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 4:
									if (lottery.getFournums() > lottery.getFourlucknums()) {
										isZhongJiang = "1";
										lottery.setFourlucknums(lottery.getFourlucknums() + 1);
										msg = "四等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 5:
									if (lottery.getFivenums() > lottery.getFivelucknums()) {
										isZhongJiang = "1";
										lottery.setFivelucknums(lottery.getFivelucknums() + 1);
										msg = "五等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								case 6:
									if (lottery.getSixnums() > lottery.getSixlucknums()) {
										isZhongJiang = "1";
										lottery.setSixlucknums(lottery.getSixlucknums() + 1);
										msg = "六等奖";
									} else {
										msg = "谢谢参与";
									}
									break;
								}

								wxLotteryService.saveOrUpdate(lottery);

								if (StringUtils.isNotEmpty(isZhongJiang) && "1".equals(isZhongJiang)) {

									String sn = RandomStringUtils.random(13,
											"1234567890abc1234567890defghi1234567890jklmno1234567890pqrs1234567890tuvwxyz");

									HttpSession session = request.getSession();
									Map<String, Object> sesessionData = new HashMap<String, Object>();
									sesessionData.put("msg", msg);
									sesessionData.put("sn", sn);
									session.setAttribute("lottery_" + uid + "_" + lid, sesessionData);

									j.put("sn", sn);
									j.put("prizetype", winNum);

									success = true;
								}
							} else {
								//没有匹配到奖项
								isZhongJiang = "0";
								msg = "谢谢参与";
							}

						}else{
							//中奖概率过滤后没中奖
							isZhongJiang = "0";
							msg = "谢谢参与";
						}
					}

				}
				j.put("canrqnums", lottery.getCanrqnums());

			}
		}

		j.put("norun", norun);
		j.put("usenums", usenums);
		j.put("success", success);

		return j.toString();
	}

	/**
	 * 我的奖品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getMyPrize")
	@ResponseBody
	public JSONObject getMyPrize(HttpServletRequest request) {

		JSONObject j = new JSONObject();

		int pageNo = 1;
		int pageSize = 10;

		String uid = request.getParameter("uid");
		if (StringUtils.isEmpty(uid) || !StringUtils.isNumeric(uid)) {
			j.accumulate("resultCode", 0);
			j.accumulate("resultMsg", "会员不存在");
			return j;
		}

		MemberEntity member = wxLotteryService.getEntity(MemberEntity.class, String.valueOf(uid));

		if (member == null) {
			j.accumulate("resultCode", 0);
			j.accumulate("resultMsg", "会员不存在");
			return j;
		}

		pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? pageSize : Integer.valueOf(request.getParameter("pageSize"));
		pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? pageNo : Integer.valueOf(request.getParameter("pageNo"));

		String sql = "select l.title,lr.prize,lr.sn,lr.time,l.starpicurl as picurl,l.txt,lr.sendstutas " +
				" from tp_lottery_record as lr " +
				" left join tp_lottery as l on lr.lid=l.id " +
				" where " +
				" lr.islottery=1 " +
				" and lr.wecha_id=? ";
		List<Map<String, Object>> data = wxLotteryService.findForJdbcParam(sql, pageNo, pageSize,uid);
		j.accumulate("resultCode", 1);
		j.accumulate("pageNo", pageNo);
		j.accumulate("pageSize", pageSize);
		
		int pageCount = (int)Math.ceil((double)wxLotteryService.findForJdbc(sql, uid).size() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		
		j.accumulate("pageCount", pageCount);
		j.accumulate("items", JSONArray.fromObject(data, getJsonConfig()));
		return j;
	}
	
	/**
	 * 活动列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/lotteryList")
	@ResponseBody
	public JSONObject lotteryList(HttpServletRequest request){
		JSONObject j=new JSONObject();
		
		String lotteryType=request.getParameter("lotteryType");//1代表大转盘、2代表刮刮卡、3代表砸金蛋
		
		if(StringUtils.isEmpty(lotteryType)|| !StringUtils.isNumeric(lotteryType)){
			j.accumulate("resultCode", 0);
			j.accumulate("resultMsg", "lotteryType\t参数类型不正确");
			return j;
		}
		int pageNo = 1;
		int pageSize = 10;
		
		pageSize = StringUtils.isEmpty(request.getParameter("pageSize")) ? 10 : Integer.valueOf(request.getParameter("pageSize"));
		pageNo = StringUtils.isEmpty(request.getParameter("pageNo")) ? 1 : Integer.valueOf(request.getParameter("pageNo"));
		
		String sql = "select id,title,statdate,starpicurl,enddate,endpicurl " +
			" from cms_lottery " +
			" where " +
			" status=1 " +
			" and type=? " +
			" order by createdate desc ";
		List<Map<String, Object>> data = wxLotteryService.findForJdbcParam(sql, pageNo, pageSize,lotteryType);
		j.accumulate("resultCode", 1);
		j.accumulate("pageNo", pageNo);
		j.accumulate("pageSize", pageSize);
		
		int pageCount = (int)Math.ceil((double)wxLotteryService.findForJdbc(sql, lotteryType).size() / (double)pageSize);
		if(pageCount <= 0){
			pageCount = 1;
		}
		
		j.accumulate("pageCount", pageCount);
		j.accumulate("items", JSONArray.fromObject(data, getJsonConfig()));
		return j;
	}

	/**
	 * 设置过滤值为空的属性，使得生成的 json 字符串只包含非空的值
	 * 
	 * @return
	 */
	public JsonConfig getJsonConfig() {
		JsonConfig jsonConfig = new JsonConfig();
		// 设置为""的String类型转为null
//		jsonConfig.registerDefaultValueProcessor(String.class, new DefaultValueProcessor() {
//			public Object getDefaultValue(Class type) {
//				return null;
//			}
//		});
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			@Override
			public boolean apply(Object source, String name, Object value) {
				return value == null;
			}
		});
		return jsonConfig;
	}
}
