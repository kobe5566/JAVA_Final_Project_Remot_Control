
public class Tools {
	Tools(){}
	public static final int bytesToInt(byte[] byteArray){
		return (byteArray[3] & 0xFF)
    			| (byteArray[2] & 0xFF) << 8
    			| (byteArray[1] & 0xFF) << 16
    			| (byteArray[0] & 0xFF) <<24;
	}
	public static final byte[] intToBytes(int num){
		return new byte[]{
				(byte)(num>>>24),
				(byte)(num>>>16),
				(byte)(num>>>8),
				(byte)(num)
				};
	}
	
}
