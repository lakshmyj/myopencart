package utilities;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.DataProvider;

public class DataProviders {

	public Logger logger = LogManager.getLogger(this.getClass());
	
@DataProvider(name="LoginData")
public String[][] getData() throws IOException
{
	String path=".\\testData\\loginTestData.xlsx";
	ExcelUtility excelUtils = new ExcelUtility(path);
	int totalRows = excelUtils.getRowCount("Sheet1");
	int totalColumns = excelUtils.getCellCount("Sheet1", 1);
	//logger.info("Reading data from excel: RowCount "+totalRows+" ColumnCount: "+totalColumns);
	//System.out.println("Reading data from excel: RowCount "+totalRows+" ColumnCount: "+totalColumns);
	String loginData[][]= new String[totalRows][totalColumns];
	
	for(int r=1;r<=totalRows;r++)
	{
		for(int c=0;c<totalColumns;c++)
		{
			loginData[r-1][c] = excelUtils.getCellData("Sheet1", r, c);
			//logger.info("Excel Data "+excelUtils.getCellData("Sheet1", r, c));
		}
	}
	
	return loginData;
}
}
