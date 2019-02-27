//###################################
/**
 * validate.method.extend jquery-validate 规则扩展
 * @author hemf(bee) 2504637134@qq.com
 * @version 2.0
 */
//###################################
require( [ "jquery", "jquery.validate.min" ], function( jQuery ) {
	(function(){
		// 判断数值类型，包括整数和小数
		jQuery.validator.addMethod("isNumber", function(value, element) {       
			return this.optional(element) || /^[-\+]?\d+$/.test(value) || /^[-\+]?\d+(\.\d+)?$/.test(value);       
		}, "必须为整数或小数");  

		// 正整数
		jQuery.validator.addMethod("isPositiveNumber", function(value, element) {       
			 var aint=parseInt(value);	
			 return aint>0&& (aint+"")==value; 
		}, "必须为正整数");
		
		// 只能输入[0-9]数字
		jQuery.validator.addMethod("isDigits", function(value, element) {       
			return this.optional(element) || /^\d+$/.test(value);       
		}, "只能输入0-9数字");  

		// 判断中文字符 
		jQuery.validator.addMethod("isChinese", function(value, element) {       
			return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
		}, "只能是中文字符。");


		// 判断中文字符
		jQuery.validator.addMethod("isUnChinese", function(value, element) {
			return this.optional(element) || !/^[\u0391-\uFFE5]+$/.test(value);
		}, "非中文字符。");

		// 判断英文字符 
		jQuery.validator.addMethod("isEnglish", function(value, element) {       
			return this.optional(element) || /^[A-Za-z]+$/.test(value);       
		}, "只能是英文字符。");   

		// 手机号码验证    
		jQuery.validator.addMethod("isMobile", function(value, element) {    
			var length = value.length;    
			return this.optional(element) || (length == 11 && /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/.test(value));    
		}, "不合法。");

		// 电话号码验证    
		jQuery.validator.addMethod("isPhone", function(value, element) {    
			var tel = /^(\d{3,4}-?)?\d{7,9}$/g;    
			return this.optional(element) || (tel.test(value));    
		}, "不合法。");

		// 联系电话(手机/电话皆可)验证   
		jQuery.validator.addMethod("isTel", function(value,element) {   
			var length = value.length;   
			var mobile = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;   
			var tel = /^(\d{3,4}-?)?\d{7,9}$/g;       
			return this.optional(element) || tel.test(value) || (length==11 && mobile.test(value));   
		}, "不合法"); 

		// 匹配qq      
		jQuery.validator.addMethod("isQq", function(value, element) {       
			return this.optional(element) || /^[1-9]\d{4,12}$/;       
		}, "不合法");   

		// 邮政编码验证    
		jQuery.validator.addMethod("isZipCode", function(value, element) {    
			var zip = /^[0-9]{6}$/;    
			return this.optional(element) || (zip.test(value));    
		}, "不合法。");  

		// 匹配密码，以字母开头，长度在6-12之间，只能包含字符、数字和下划线。      
		jQuery.validator.addMethod("isPwd", function(value, element) {       
			return this.optional(element) || /^[a-zA-Z]\w{6,12}$/.test(value);       
		}, "英文,下划线。");

		// 身份证号码验证
		jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
			//var idCard = /^(\d{6})()?(\d{4})(\d{2})(\d{2})(\d{3})(\w)$/;   
			return this.optional(element) || isIdCardNo(value);    
		}, "不合法。"); 

		// IP地址验证   
		jQuery.validator.addMethod("ip", function(value, element) {    
			return this.optional(element) || /^(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.)(([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))\.){2}([1-9]|([1-9]\d)|(1\d\d)|(2([0-4]\d|5[0-5])))$/.test(value);    
		}, "不合法。");

		// 字符验证，只能包含中文、英文、数字、下划线等字符。    
		jQuery.validator.addMethod("stringCheck", function(value, element) {       
			return this.optional(element) || /^[a-zA-Z0-9\u4e00-\u9fa5-_]+$/.test(value);       
		}, "中文,英文,数字,下划线");   

		// 匹配english  
		jQuery.validator.addMethod("isEnglish", function(value, element) {       
			return this.optional(element) || /^[A-Za-z]+$/.test(value);       
		}, "匹配英文");   

		// 匹配汉字  
		jQuery.validator.addMethod("isChinese", function(value, element) {       
			return this.optional(element) || /^[\u4e00-\u9fa5]+$/.test(value);       
		}, "匹配汉字");   

		// 匹配中文(包括汉字和字符) 
		jQuery.validator.addMethod("isChineseChar", function(value, element) {       
			return this.optional(element) || /^[\u0391-\uFFE5]+$/.test(value);       
		}, "匹配中文(汉字货字符) "); 

		// 判断是否为合法字符(a-zA-Z0-9-_)
		jQuery.validator.addMethod("isRightfulString", function(value, element) {       
			return this.optional(element) || /^[A-Za-z0-9_-]+$/.test(value);       
		}, "合法字符(a-zA-Z0-9-_)");   

		// 判断是否包含中英文特殊字符，除英文"-_"字符外
		jQuery.validator.addMethod("isContainsSpecialChar", function(value, element) {  
			var reg = RegExp(/[(\ )(\`)(\~)(\!)(\@)(\#)(\$)(\%)(\^)(\&)(\*)(\()(\))(\+)(\=)(\|)(\{)(\})(\')(\:)(\;)(\')(',)(\[)(\])(\.)(\<)(\>)(\/)(\?)(\~)(\！)(\@)(\#)(\￥)(\%)(\…)(\&)(\*)(\（)(\）)(\—)(\+)(\|)(\{)(\})(\【)(\】)(\‘)(\；)(\：)(\”)(\“)(\’)(\。)(\，)(\、)(\？)]+/);   
			return this.optional(element) || !reg.test(value);       
		}, "中英文特殊字符");   

		jQuery.validator.addMethod("isIdCardNo", function(value,element){
			return this.optional(element) || isIdCardNo(value);
		},"非法的身份证号码");

		jQuery.validator.addMethod("isBankCardNo",function(value,element){
			return this.optional(element) || luhmCheck(value);
		},"非法的储蓄卡号码");

		//身份证号码的验证规则
		isIdCardNo = function (idcard){
			if (typeof idcard == 'string') idcard = idcard.toUpperCase();
			var area={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};

			var idcard,Y,JYM;
			var S,M;
			var idcard_array = new Array();
			idcard_array = idcard.split("");
			// 地区检验
			if(area[parseInt(idcard.substr(0,2))]==null) return  false;

			// 身份号码位数及格式检验
			switch(idcard.length){
			case 15:
				if ( (parseInt(idcard.substr(6,2))+1900) % 4 == 0 || ((parseInt(idcard.substr(6,2))+1900) % 100 == 0 && (parseInt(idcard.substr(6,2))+1900) % 4 == 0 )){
					ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}$/;// 测试出生日期的合法性
				} else {
					ereg=/^[1-9][0-9]{5}[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}$/;// 测试出生日期的合法性
				}
				if(!ereg.test(idcard)) 
					false;
				return false;
				break;
			case 18:
				// 18位身份号码检测
				// 出生日期的合法性检查
				// 闰年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))
				// 平年月日:((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))
				if ( parseInt(idcard.substr(6,4)) % 4 == 0 || (parseInt(idcard.substr(6,4)) % 100 == 0 && parseInt(idcard.substr(6,4))%4 == 0 )){
					ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|[1-2][0-9]))[0-9]{3}[0-9Xx]$/;// 闰年出生日期的合法性正则表达式
				} else {
					ereg=/^[1-9][0-9]{5}19[0-9]{2}((01|03|05|07|08|10|12)(0[1-9]|[1-2][0-9]|3[0-1])|(04|06|09|11)(0[1-9]|[1-2][0-9]|30)|02(0[1-9]|1[0-9]|2[0-8]))[0-9]{3}[0-9Xx]$/;// 平年出生日期的合法性正则表达式
				}
				if(ereg.test(idcard)){// 测试出生日期的合法性
					// 计算校验位
					S = (parseInt(idcard_array[0]) + parseInt(idcard_array[10])) * 7
					+ (parseInt(idcard_array[1]) + parseInt(idcard_array[11])) * 9
					+ (parseInt(idcard_array[2]) + parseInt(idcard_array[12])) * 10
					+ (parseInt(idcard_array[3]) + parseInt(idcard_array[13])) * 5
					+ (parseInt(idcard_array[4]) + parseInt(idcard_array[14])) * 8
					+ (parseInt(idcard_array[5]) + parseInt(idcard_array[15])) * 4
					+ (parseInt(idcard_array[6]) + parseInt(idcard_array[16])) * 2
					+ parseInt(idcard_array[7]) * 1 
					+ parseInt(idcard_array[8]) * 6
					+ parseInt(idcard_array[9]) * 3 ;
					Y = S % 11;
					M = "F";
					JYM = "10X98765432";
					M = JYM.substr(Y,1);// 判断校验位
					if(M == idcard_array[17]) return true; // 检测ID的校验位
					else return false; false;
				}
				else return  false;
				break;
			default:
				return  false;
			break;
			}
		};

		//银行卡号校验
		//Description:  银行卡号Luhm校验
		//Luhm校验规则：16位银行卡号（19位通用）:
		// 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
		// 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
		// 3.将加法和加上校验位能被 10 整除。
		luhmCheck = function(bankno){
			if (bankno.length < 16 || bankno.length > 19) {
				//$("#banknoInfo").html("银行卡号长度必须在16到19之间");
				return false;
			}
			var num = /^\d*$/;  //全数字
			if (!num.exec(bankno)) {
				//$("#banknoInfo").html("银行卡号必须全为数字");
				return false;
			}
			//开头6位
			var strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
			if (strBin.indexOf(bankno.substring(0, 2))== -1) {
				//$("#banknoInfo").html("银行卡号开头6位不符合规范");
				return false;
			}
			var lastNum=bankno.substr(bankno.length-1,1);//取出最后一位（与luhm进行比较）

			var first15Num=bankno.substr(0,bankno.length-1);//前15或18位
			var newArr=new Array();
			for(var i=first15Num.length-1;i>-1;i--){    //前15或18位倒序存进数组
				newArr.push(first15Num.substr(i,1));
			}
			var arrJiShu=new Array();  //奇数位*2的积 <9
			var arrJiShu2=new Array(); //奇数位*2的积 >9

			var arrOuShu=new Array();  //偶数位数组
			for(var j=0;j<newArr.length;j++){
				if((j+1)%2==1){//奇数位
					if(parseInt(newArr[j])*2<9)
						arrJiShu.push(parseInt(newArr[j])*2);
					else
						arrJiShu2.push(parseInt(newArr[j])*2);
				}
				else //偶数位
					arrOuShu.push(newArr[j]);
			}

			var jishu_child1=new Array();//奇数位*2 >9 的分割之后的数组个位数
			var jishu_child2=new Array();//奇数位*2 >9 的分割之后的数组十位数
			for(var h=0;h<arrJiShu2.length;h++){
				jishu_child1.push(parseInt(arrJiShu2[h])%10);
				jishu_child2.push(parseInt(arrJiShu2[h])/10);
			}        

			var sumJiShu=0; //奇数位*2 < 9 的数组之和
			var sumOuShu=0; //偶数位数组之和
			var sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
			var sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
			var sumTotal=0;
			for(var m=0;m<arrJiShu.length;m++){
				sumJiShu=sumJiShu+parseInt(arrJiShu[m]);
			}

			for(var n=0;n<arrOuShu.length;n++){
				sumOuShu=sumOuShu+parseInt(arrOuShu[n]);
			}

			for(var p=0;p<jishu_child1.length;p++){
				sumJiShuChild1=sumJiShuChild1+parseInt(jishu_child1[p]);
				sumJiShuChild2=sumJiShuChild2+parseInt(jishu_child2[p]);
			}      
			//计算总和
			sumTotal=parseInt(sumJiShu)+parseInt(sumOuShu)+parseInt(sumJiShuChild1)+parseInt(sumJiShuChild2);

			//计算Luhm值
			var k= parseInt(sumTotal)%10==0?10:parseInt(sumTotal)%10;        
			var luhm= 10-k;

			if(lastNum==luhm){
				$("#banknoInfo").html("Luhm验证通过");
				return true;
			}
			else{
				$("#banknoInfo").html("银行卡号必须符合Luhm校验");
				return false;
			}        
		};
	})(jQuery);
} );