public class TaxBracket {
    //gotta do it so that if you supercede a bracket a new one just replace the end of previous with start of current and beggining of next with end of currentspo
    private int bottom;
    private int top;
    private int baseTax;
    private double taxDollar;
    private int taxDollarOutput;
//    public static void main(String[] args)
//    {
//        TaxBracket bracket1 = new TaxBracket(0,"20000",0,0);
//        TaxBracket bracket2 = new TaxBracket(20001,"52000",100,12);
//        System.out.println(bracket2.calculateTax(27000));
//    }
    public TaxBracket(){}
    public TaxBracket(String bottom, String top, String baseTax, String taxDollar)
    {
        if(top.equals("~"))
        {
            this.top = Integer.MAX_VALUE;
        }
        else{
            this.top = Integer.parseInt(top);
        }
        this.bottom = Integer.parseInt(bottom);
        this.baseTax = Integer.parseInt(baseTax);
        this.taxDollar = (Double.parseDouble(taxDollar)/100);
        this.taxDollarOutput = Integer.parseInt(taxDollar);

    }

    public String outputFormat(){
        String output;
        if(top == Integer.MAX_VALUE)
        {
            return bottom+" ~ "+baseTax+" "+taxDollarOutput;
        }
        else{
            return bottom+" "+top+" "+baseTax+" "+taxDollarOutput;
        }

    }

    //used for checking if the income is within the range.
    public boolean inRange(int income)
    {
        if(income >= this.bottom && income <= this.top){
            return true;
        }
        return false;
    }
    //returns an int based on if the bottom is contained within the current range, and same for top.
    // Uses 1 and 2 so that can identify which sections are in range. used for checking for new brackets.
    public int inRange(int bottom, int top)
    {
        int x =0;
        if(bottom >= this.bottom && bottom <= this.top){
            x++;
        }
        if(top >= this.bottom && top <= this.top)
        {
            x+=2;
        }
        if(top == this.top && bottom == this.bottom)
        {
            x = 4;
        }
        if(bottom < this.bottom && top > this.top)
        {
            x = 5;
        }
        return x;
    }


    public double calculateTax(int income)
    {
        double taxed;
        taxed = baseTax;
        if(bottom == 0)
        {
            taxed += (income-bottom)*taxDollar;
        }
        else {
            taxed += (income - bottom + 1) * taxDollar;
        }
        return taxed;
    }
    //Getters and Setters
    public int getBottom() {
        return bottom;
    }

    public void setBottom(int bottom) {
        this.bottom = bottom;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public int getBasetax() {
        return baseTax;
    }

    public void setBasetax(int baseTax) {
        this.baseTax = baseTax;
    }

    public double getTaxdollar() {
        return taxDollar;
    }
    public int getTaxDollarInt()
    {
        return taxDollarOutput;
    }
    public void setTaxdollar(int taxDollar) {
        this.taxDollar = taxDollar;
    }


}
