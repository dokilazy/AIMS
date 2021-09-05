package libraries.objects;

import java.util.ArrayList;

import libraries.generalFunctions.ExcelReader;

public class VRMExcel {
	private ArrayList<String> vrmNumber = null;
	private ArrayList<String> cameraEncoderNumber = null;
	private ArrayList<String> cameraSite = null;
	private ArrayList<String> exceptionDate = null;

	public ArrayList<String> getVrmNumber() {
		return vrmNumber;
	}

	public void setVrmNumber(ArrayList<String> vrmNumber) {
		this.vrmNumber = vrmNumber;
	}

	public ArrayList<String> getcameraEncoderNumber() {
		return cameraEncoderNumber;
	}

	public void setCameraEncoderNumber(ArrayList<String> cameraEncoderNumber) {
		this.cameraEncoderNumber = cameraEncoderNumber;
	}

	public ArrayList<String> getCameraSite() {
		return cameraSite;
	}

	public void setCameraSite(ArrayList<String> cameraSite) {
		this.cameraSite = cameraSite;
	}

	public ArrayList<String> getExceptionDate() {
		return exceptionDate;
	}

	public void setExceptionDate(ArrayList<String> exceptionDate) {
		this.exceptionDate = exceptionDate;
	}

}
