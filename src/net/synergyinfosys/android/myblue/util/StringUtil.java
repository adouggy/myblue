package net.synergyinfosys.android.myblue.util;

public enum StringUtil {
	INSTACE;

	StringUtil() {
	}
	
	public static String trim(String str){
		if( str == null )
			return "";
		
		return str.trim();
	}

	/**
	 * make String shorter, add ... at the end if String.length > 10
	 * may have trouble dealing with Chinese
	 * 
	 * @param str
	 * @param len
	 * @return
	 */
	public String shorten(String str, int len) {
		if( str == null )
			return null;
		
		if( str.length()>len ){
			if( len>10 ){
				return str.substring(0, len - 3) + "...";
			}else{
				return str.substring(0, len);
			}
		}
		
		return str;
	}
	
	public boolean isNoneBlank(String str){
		if( str == null || str.trim().length()==0 )
			return false;
		
		return true;
	}
}
