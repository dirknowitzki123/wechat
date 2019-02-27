package com.by.core.log;

public enum BusiOperaLogEventEnum {
	CONTRACT_GENERATE(1,"调合同服务生成合同"),
	CONTRACT_GET_SIGN_URL(2,"调合同服务获取待签约列表"),
	CONTRACT_QUERY_SIGN_INFO(3,"调合同服务查询签约状态"),
	CONTRACT_DOWNLOAD_PDF(4,"调合同服务下载合同PDF"),
	;
	
	private int number;
	private String desc;

	private BusiOperaLogEventEnum(int number,String desc){
		this.setNumber(number);
		this.setDesc(desc);
	}
	public int getNumber() {
		return number;
	}
	private void setNumber(int number) {
		this.number = number;
	}
	public String getDesc() {
		return desc;
	}
	private void setDesc(String desc) {
		this.desc = desc;
	}
	public BusiOperaLogEventEnum getEnumByNum(int number){
		for (BusiOperaLogEventEnum thirdPartyServiceEnum: BusiOperaLogEventEnum.values()) {
			if(thirdPartyServiceEnum.getNumber()==number){
				return thirdPartyServiceEnum;
			}
		}
		return null;
	}

}
