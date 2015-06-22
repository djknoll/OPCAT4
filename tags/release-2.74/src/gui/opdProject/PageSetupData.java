package gui.opdProject;

import java.awt.print.PageFormat;
import java.awt.print.Paper;

public class PageSetupData extends PageFormat
{
	private boolean prjName = true;
	private boolean opdName = true;
	private boolean pageNumber = true;
	private boolean prjCreator = false;
	private boolean printTime = false;
	private boolean centerView = true;
	private boolean fitToPage = false;
	private boolean doNotAlign = false;
	private int marginWidth = 36;
	private boolean applyed;

	public PageSetupData(){
		setOrientation(PageFormat.REVERSE_LANDSCAPE);
		Paper p = getPaper();
		double h = getWidth();
		double w = getHeight();

		p.setImageableArea(marginWidth, marginWidth,
										w-marginWidth*2, h-marginWidth*2);
		setPaper(p);

	}
	//public PrintProperties(){}
	public boolean isCenterView()
	{
		return centerView;
	}
	public void setCenterView(boolean centerView)
	{
		this.centerView = centerView;
	}
	public boolean isFitToPage()
	{
		return fitToPage;
	}
	public void setFitToPage(boolean fitToPage)
	{
		this.fitToPage = fitToPage;
	}
	public boolean isDoNotAlign()
	{
		return doNotAlign;
	}
	public void setDoNotAlign(boolean doNotAlign)
	{
		this.doNotAlign = doNotAlign;
	}
	public boolean isPageNumber()
	{
		return pageNumber;
	}
	public void setPageNumber(boolean pageNumber)
	{
		this.pageNumber = pageNumber;
	}
	public boolean isPrintTime()
	{
		return printTime;
	}
	public void setPrintTime(boolean printTime)
	{
		this.printTime = printTime;
	}
	public boolean isPrjCreator()
	{
		return prjCreator;
	}
	public void setPrjCreator(boolean prjCreator)
	{
		this.prjCreator = prjCreator;
	}
	public boolean isPrjName()
	{
		return prjName;
	}
	public void setPrjName(boolean prjName)
	{
		this.prjName = prjName;
	}
	public boolean isOpdName()
	{
		return opdName;
	}
	public void setOpdName(boolean opdName)
	{
		this.opdName = opdName;
	}
	public boolean isApplyed()
	{
		return applyed;
	}
	public void setApplyed(boolean applyed)
	{
		this.applyed = applyed;
	}
	public int getMarginWidth()
	{
		return marginWidth;
	}
	public void setMarginWidth(int marginWidth)
	{
		this.marginWidth = marginWidth;
	}
}