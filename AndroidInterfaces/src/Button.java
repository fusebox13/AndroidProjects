public class Button {
    OnClickListener listener = null;
    
    void setOnClickListener(OnClickListener listener)
    {
        this.listener = listener;
    }
    
    void simulatedButtonClick()
    {
        if (listener != null)
            listener.onClick();
    }

}