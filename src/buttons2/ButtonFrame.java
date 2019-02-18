package buttons2;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
/**
 * 对于keyEvent,那个组件首先需要请求焦点才能触发KeyEvent事件
 * @author REN
 *
 */
public abstract class ButtonFrame extends JFrame {
    public static final int DEFAULT_WIDTH = 300;
    public static final int DEFAULT_HEIGHT = 200;
    protected JPanel panel;
    
    protected JButton yellowButton;
    protected JButton blueButton;
    protected JButton redButton;
    
    protected abstract void addEventHandlers();
    
    public ButtonFrame()
    {
        setSize(DEFAULT_WIDTH, DEFAULT_WIDTH);
        
        panel = new JPanel();
        add(panel);
        
        yellowButton = new JButton("Yellow");
        blueButton = new JButton("Blue");
        redButton = new JButton("Red");
        
        panel.add(yellowButton);
        panel.add(blueButton);
        panel.add(redButton);
        
        addEventHandlers();
        /**
         * addComponentListener, 
         * addFocusListener, \
         * addHierarchyBoundsListener, 
         * addHierarchyListener, 
         * addInputMethodListener, addKeyListener, \
         * addMouseListener, addMouseMotionListener, addMouseWheelListener,
         */
    }
}
