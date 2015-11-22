package models;

public class MenuItem {

	private int id;
	private int defaultIcon;
	private int selectedIcon;
	private String title;
	private boolean isSelected;

	public MenuItem(int defaultIcon, int selectedIcon, String title,
			boolean isSelected) {
		super();
		this.defaultIcon = defaultIcon;
		this.selectedIcon = selectedIcon;
		this.title = title;
		this.isSelected = isSelected;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public int getDefaultIcon() {
		return defaultIcon;
	}

	public void setDefaultIcon(int defaultIcon) {
		this.defaultIcon = defaultIcon;
	}

	public int getSelectedIcon() {
		return selectedIcon;
	}

	public void setSelectedIcon(int selectedIcon) {
		this.selectedIcon = selectedIcon;
	}

}
