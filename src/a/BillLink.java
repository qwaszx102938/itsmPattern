package a;

public class BillLink {
  private String linkString;

public BillLink(String linkString) {
	this.linkString=linkString;
}  
  
public String getLinkString() {
	return linkString;
}

public void setLinkString(String linkString) {
	this.linkString = linkString;
}

@Override
public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
			+ ((linkString == null) ? 0 : linkString.hashCode());
	return result;
}

@Override
public boolean equals(Object obj) {
	if (this == obj)
		return true;
	if (obj == null)
		return false;
	if (getClass() != obj.getClass())
		return false;
	BillLink other = (BillLink) obj;
	if (linkString == null) {
		if (other.linkString != null)
			return false;
	} else if (!linkString.equals(other.linkString))
		return false;
	return true;
}
  

}
