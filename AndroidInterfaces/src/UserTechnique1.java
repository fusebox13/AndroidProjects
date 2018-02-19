public class UserTechnique1 implements OnClickListener{
    
    public void onClick()
    {
        System.out.println("onClick was called");
    }
    
    public void myActivity()
    {
        Button b = new Button();
        b.setOnClickListener(this);
        
        b.simulatedButtonClick();
    }
    
    public static void main(String[] args)
    {
        UserTechnique1 ut = new UserTechnique1();
        ut.myActivity();
    }

}