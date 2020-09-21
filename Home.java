import java.util.*;
import java.text.*;
import java.io.*;


 class Cat
{
	private Long catId=System.currentTimeMillis();
	private String name;

	public Cat(String name)
	{
		this.name = name;
	}
	public Cat(Long catId, String name)
	{
		this.catId=catId;
		this.name = name;
	}
	public Cat()
	{

	}
	public Long getCatId()
	{
		return catId;
	}
	public void setCatId(Long catId)
	{
		this.catId = catId;
	}
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
}

class DU
{
	public static final String[] MONTHS = {"January", "February", "March", "April", "May", "June", " July", "August", "September", "October", "November", "December"};

	public static Date sToD(String dateAsString)  
	{
	    try
	    {
	    	SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		    return df.parse(dateAsString);
	    }
	    catch(ParseException ex)
	    {
	    	ex.printStackTrace();
	    	return null;
	    }
    }

    public static String dateToString(Date date)  
	{
	    SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
	    return df.format(date);
	}    

	public static String getYearAndMonth(Date date)  
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy,MM");
	    return df.format(date);
	}

	public static Integer getYear(Date date)  
	{
		SimpleDateFormat df=new SimpleDateFormat("yyyy");
	    return new Integer(df.format(date));
	}

	public static String getMonthName(Integer monthNo)  
	{
		return MONTHS[monthNo-1];
	}
}

 class Exp
{
	private Long expenseId = System.currentTimeMillis();
    private Long catId;
    private Float amount;
    private Date date;
    private String remark;

    public Exp()
    {

    }

    public Exp(Long catId, Float amount, Date date, String remark)
    {
    	this.catId = catId;
    	this.amount = amount;
    	this.date = date;
    	this.remark = remark;
    }

    public Long getExpId()
    {
    	return expenseId;
    }

    public void setExpId(Long expenseId)
    {
    	this.expenseId = expenseId;
    }

    public Float getAmount()
    {
        return amount;
    }

    public void setAmount(Float amount)
    {
        this.amount = amount;
    }

    public Long getCatId()
    {
        return catId;
    }

    public void setCatId(Long catId)
    {
        this.catId = catId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}

 class RepS
{
	private Rep repo = Rep.getRep();

	public Map<String,Float> calculateMonthlyTotal()  
	{
		Map<String,Float> m =new TreeMap();
		for (Exp exp : repo.expList)
		{
			Date expDate = exp.getDate();
			String yearMonth=DU.getYearAndMonth(expDate);
			if(m.containsKey(yearMonth))
			{
				
				Float total = m.get(yearMonth);
				total=total+exp.getAmount();
				m.put(yearMonth, total); 
			}
			else
			{
				
				m.put(yearMonth, exp.getAmount());
			}
		}
		return m; 
	}


	public String getCatNameById(Long catId)  
	{
		for(Cat c : repo.catList)
		{
			if(c.getCatId().equals(catId))
			{
				return c.getName();
			}
		}
		return null; 
	}
}

class Rep
{
	public List<Exp> expList=new ArrayList();
	public List<Cat> catList=new ArrayList(); 
	private static Rep repository;
	private Rep()
	{

	}
	public static Rep getRep() 
	{
		if(repository==null)
		{
			repository = new Rep();
        }
        return repository; 
	}
		
}

 class HomeBM
{
	Rep repo = Rep.getRep();
	RepS reportService=new RepS();

	private Scanner in = new Scanner(System.in);
	private int ch;

	public HomeBM()
	{
		prepareSampleData();
	}
	public void showMenu()    
	{
		while(true)
		{
			printMenu();
			switch(ch)
			{
				case 1:
				    addCat();
				    break;
				case 2:
				    catLst();
				    break;
				case 3:
				    expEny();
				    break;
				case 4:
				    expLt();
				    break;
				case 5:
				    monEst();
				    break;              
				case 0:
				    System.exit(0);
				    break;
			}
		}
	}

	public void printMenu()     
	{
		System.out.println("********** Menu ************");
		System.out.println("1. Add Category");
		System.out.println("2. Category List");
		System.out.println("3. Expense Entry");
		System.out.println("4. Expense List");
		System.out.println("5. Monthly Expense List");
		System.out.println("0. Exit");
		System.out.println("*************************");
		System.out.println("Enter your choice: ");
		ch = in.nextInt(); 
	}



	public void addCat()  
	{
		in.nextLine();   
		System.out.print("Enter Cat Name: ");
		String catName=in.nextLine();
		Cat cat=new Cat(catName);
        repo.catList.add(cat);
        System.out.println("Success: Cat Added");
	}

	public void catLst() 
	{
		System.out.println("Cat List");
		List<Cat> clist=repo.catList;
		for(int i=0; i<repo.catList.size(); i++)
		{
			Cat c = clist.get(i);
            System.out.println((i+1)+". "+ c.getName()+", "+c.getCatId());
		}
	}

	public void expEny()  //This method enters expense details
	{
		System.out.println("Enter Details for Exp Entry ...");
		catLst();
		System.out.print("Choose Cat: ");
		int catChoice = in.nextInt();
		Cat selectedCat = repo.catList.get(catChoice-1);

		System.out.print("Enter Amount : ");
		float amount = in.nextFloat();

		System.out.print("Enter Remark : ");
		in.nextLine();
		String remark=in.nextLine();

        System.out.println("Enter Date(DD/MM/YYYY): ");
        String dateAsString = in.nextLine();
        Date date=DU.sToD(dateAsString);

        //Add Exp detail in Exp object
		Exp exp = new Exp();
		exp.setCatId(selectedCat.getCatId());
		exp.setAmount(amount);
		exp.setRemark(remark);
		exp.setDate(date);

		//store expense object in repository
		repo.expList.add(exp);
		System.out.println("Success: Exp Added");
	}

	public void expLt()   //The method prints all entered expenses
	{
		System.out.println("Exp Listing ..."); 
		List<Exp> expList=repo.expList;
		for(int i=0; i<expList.size(); i++)
		{
			Exp exp=expList.get(i);
			String catName=reportService.getCatNameById(exp.getCatId());
			String dateString= DU.dateToString(exp.getDate());
			System.out.println((i+1)+". "+catName+", "+ exp.getCatId()+" "+exp.getAmount()+ ", "+exp.getRemark()+", "+exp.getDate());
		}
	}

	public void monEst()  
	{
		System.out.println("Monthly Exp Listing ..."); 
		Map<String,Float> resultMap = reportService.calculateMonthlyTotal();
		Set<String> keys=resultMap.keySet();
		for(String yearMonth : keys)
		{
			String[] arr = yearMonth.split(",");
			String year = arr[0];
			Integer monthNo = new Integer(arr[1]);
			String monthName = DU.getMonthName(monthNo);
			System.out.println(year+", "+monthName+" : "+resultMap.get(yearMonth));
		}
	}

	

	public void prepareSampleData()  
	{
		Cat catParty = new Cat("Party");
		delay();
		Cat catShopping = new Cat("Shopping");
		delay();
		Cat catGift = new Cat("Gift");

		repo.catList.add(catParty);
		repo.catList.add(catShopping);
		repo.catList.add(catGift);

		Exp e1 = new Exp(catParty.getCatId(), 10000.0F, DU.sToD("01/01/2019"),"N/A");
		delay();
		Exp e2 = new Exp(catParty.getCatId(), 2000.0F, DU.sToD("02/01/2019"),"N/A");
		delay();

		Exp e3 = new Exp(catShopping.getCatId(), 200.0F, DU.sToD("01/02/2019"),"N/A");
		delay();
		Exp e4 = new Exp(catParty.getCatId(), 100.0F, DU.sToD("02/02/2019"),"N/A");
		delay();

		Exp e5 = new Exp(catGift.getCatId(), 500.0F, DU.sToD("01/12/2019"),"N/A");
		delay();

		Exp e6 = new Exp(catParty.getCatId(), 700.0F, DU.sToD("01/01/2020"),"N/A");
		delay();

		Exp e7 = new Exp(catShopping.getCatId(), 100.0F, DU.sToD("01/02/2020 "),"N/A");
		delay();

		Exp e8 = new Exp(catGift.getCatId(), 5000.0F, DU.sToD("01/03/2020"),"N/A");

		repo.expList.add(e1);
		repo.expList.add(e2);
		repo.expList.add(e3);
		repo.expList.add(e4);
		repo.expList.add(e5);
		repo.expList.add(e6);
		repo.expList.add(e7);
		repo.expList.add(e8);

	}

	private void delay() 
	{
		try
		{
			Thread.sleep(10);
		}
		catch(InterruptedException ex)
		{
			ex.printStackTrace();
		}
	}

}

public class App
{
	public static void main(String[] args)   
	{
		HomeBM service = new HomeBM();        
		service.showMenu();            
	}
}