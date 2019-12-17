package com.tw.trains;

public class Town {

	private String name;
	private String code;
	
	public Town(String code, String name)
	{
		this.code = code;
		this.name = name;
	}
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	

	@Override
	public boolean equals(Object obj) {
		if(null != obj && obj instanceof Town)
		{
			Town town = (Town)obj;
			if(this.getCode().equals(town.getCode()) && this.getName().equals(town.getName()))
			{
				return true;
			}
			
		}
		return false;
	}

	@Override
	public int hashCode() {
		return this.getCode().hashCode();
	}


	@Override
	public String toString() {
		return "Town [name=" + name + ", code=" + code + "]";
	}
	
	
	
}
