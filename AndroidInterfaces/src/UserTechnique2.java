public class UserTechnique2 {
    
    class InnerClass implements OnClickListener
    {
        public void onClick()
        {
            System.out.println("onClick was called");
        }
    }
    
    
    
    public void myActivity()
    {
        Button b = new Button();
        InnerClass ic = new InnerClass();
        b.setOnClickListener(ic);
        
        b.simulatedButtonClick();
    }
    
    public static void main(String[] args)
    {
        UserTechnique2 ut = new UserTechnique2();
        ut.myActivity();
    }

}