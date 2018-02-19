public class UserTechnique3 {
   
    public void myActivity()
    {
        Button b = new Button();
        
        
        b.setOnClickListener(  new OnClickListener()
            {
                public void onClick()
                {
                    System.out.println("onClick was called");
                }
            }                
        );
        
        b.simulatedButtonClick();
    }
    
    public static void main(String[] args)
    {
        UserTechnique3 ut = new UserTechnique3();
        ut.myActivity();
    }

}