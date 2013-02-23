package dragonkk.rs2rsps.tools.test;
public class Line {

	private String text;
	private boolean isEdited;

	@Override
	public final int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (isEdited ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public final boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Line other = (Line) obj;
		if (isEdited != other.isEdited)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public final String toString() {
		return "Line [isEdited=" + isEdited + ", text=" + text + "]";
	}

	public Line(String text, boolean isEdited) {
		this.setText(text);
		this.setEdited(isEdited);
	}

	public final void setText(String text) {
		this.text = text;
	}

	public final String getText() {
		return text;
	}

	public final void setEdited(boolean isEdited) {
		this.isEdited = isEdited;
	}

	public final boolean isEdited() {
		return isEdited;
	}
}
