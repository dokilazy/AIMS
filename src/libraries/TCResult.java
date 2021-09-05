package libraries;

public class TCResult{
	
	public Boolean Result = true;
	public String Message = "\r\n";
	
	public Boolean GetResult(){
		return Result;
	}
	
	public String GetMessage(){
		return Message;
	}
	
	public void SetResult(Boolean pResult){
		this.Result = pResult;
	}
	
	public void SetMessage(String pMessage){
		this.Message += pMessage + ".\r\n";
	}
}