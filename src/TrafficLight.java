/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Color;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextField;
import java.beans.*;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hs140
 */
public class TrafficLight extends Panel implements Serializable,Runnable,PropertyChangeListener  {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    
    private String sampleProperty;
    Thread runner;
    int interval;
    Color colr;
    Label label;
    TextField tf;
    public int getInterval() { return interval; }
    public void setInterval(int interval)
    {
        this.interval=interval;
        if(runner!=null)
        {
            runner.interrupt();
        }
    }
    public int getSeconds()
    {
        return (int)((new Date()).getTime()/1000L);
        
    }
    public void setColr(Color colr)
    {
        this.colr=colr;
    }
    public Color getColr()
    {
        return colr;
    }
      public void setLabel(Label label)
    {
        this.label=label;
    }
    public Label getLabel()
    {
        return label;
    }
          public void setTf(TextField tf)
    {
        this.tf=tf;
    }
    public TextField getTf()
    {
        return tf;
    }
    
    
    private PropertyChangeSupport propertySupport;
    
    public TrafficLight()
    {
        setSize(200,200);
      //setColr(Color.red);
      colr=Color.red;
      label=new Label();
      tf=new TextField(12);
      label.setSize(150,150);
      label.setBackground(colr);
      tf.setSize(200,200);
      tf.setText("stop");
      add(label);
       tf.setEnabled(false);
      add(tf);
        propertySupport = new PropertyChangeSupport(this);
    propertySupport.addPropertyChangeListener(this);
    
    interval=5;
    runner=new Thread(this);
          runner.start();
    }
    @Override
    public void run()
    {
        int i=getSeconds();
        do
        {
            try{
                Thread.sleep(interval*10);
            }catch(InterruptedException ie)
            {
                Logger.getLogger(TrafficLight.class.getName()).log(Level.SEVERE,null,ie); 
            }
            
            int j=getSeconds();
            propertySupport.firePropertyChange("seconds",new Integer(i),new Integer(j));
            i=j;
        }while(true);
                
    }
    
    public String getSampleProperty() {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }

    

    @Override
    public void propertyChange(PropertyChangeEvent evt) 
    { if(colr == Color.red) 
    { label.setBackground(Color.orange); 
    tf.setText("Ready"); 
    colr = Color.orange; }
    else if(colr == Color.orange) 
    { label.setBackground(Color.green);
    tf.setText("Go"); 
    colr = Color.green;
    }
    else if(colr == Color.green) 
    { label.setBackground(Color.red);
    tf.setText("Stop"); 
    colr = Color.red;
    } 
    }
   
    
    
}
