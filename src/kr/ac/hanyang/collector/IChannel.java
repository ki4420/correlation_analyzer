package kr.ac.hanyang.collector;


/**
 * Ŀ�ؼ� �Ŵ����� Ŀ�ؼ��� �����ϱ� ���� �������̽� Ŀ�ؼ��� ������ jmbc, jdbc �� ���� Ŀ�ؼ��� �����ϱ� ���� ����.
 * @author  ��ö
 */
public interface IChannel {

	public void close();
	
	public boolean isConnect();

	/**
	 * @uml.property  name="key"
	 */
	public String getKey();

}