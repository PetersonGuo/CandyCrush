import greenfoot.*;  // (World, Actor, GreenfootImage, and Greenfoot)

/**
 * Counter that displays a number.
 * 
 * @author Michael Kolling
 * @version 1.0.1
 */
public class Counter extends Actor
{
    private int value = 0;
    private int target = 0;
    private String text;
    private int stringLength;
    
    /**
     * A constructer method that creates a counter.
     */
    public Counter()
    {
        this("");
    }

    public Counter(String prefix)
    {
        text = prefix;
        stringLength = (text.length() + 5) * 16;
        setImage(new GreenfootImage(stringLength, 24));
        GreenfootImage image = getImage();
        Font font = image.getFont();
        image.setFont(font.deriveFont(24.0F));  // use larger font
        image.setColor(Color.BLACK);
        
        updateImage();
    }

    public void changeValue(int change)
    {
        value += change;
        updateImage();
    }

    public int getValue()
    {
        return value;
    }
    
    public void setValue(int value){
        this.value = value;
        updateImage();
    }

    /**
     * Make the image
     */
    private void updateImage()
    {
        GreenfootImage image = getImage();
        image.clear();
        image.drawString(text + value, 1, 18);
    }
}