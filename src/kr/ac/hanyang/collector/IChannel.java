package kr.ac.hanyang.collector;


/**
 * 커넥션 매니저가 커넥션을 관리하기 위한 인터페이스 커넥션의 단위는 jmbc, jdbc 와 같은 커넥션을 관리하기 위한 것임.
 * @author  김철
 */
public interface IChannel {

	public void close();
	
	public boolean isConnect();

	/**
	 * @uml.property  name="key"
	 */
	public String getKey();

}