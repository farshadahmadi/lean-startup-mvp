package fi.tut.lean_startup_mvp;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import elemental.html.Console;

/**
 *
 */
@Theme("mytheme")
@Widgetset("fi.tut.lean_startup_mvp.MyAppWidgetset")
public class MyUI extends UI {
	
	
	Button coffeButton;
	Button lightsButton;
	Button windowButton;
	
    FileResource alarmOffResource;
    FileResource alarmOnResource;
    FileResource coffeeOffRes;
    FileResource coffeeOnRes;
    FileResource lightOffRes;
    FileResource lightOnRes;
    FileResource windowOffRes;
    FileResource windowOnRes;
	
	
	private Table table;
    Map devices = new HashMap();
    private boolean alarmState=false;
    private boolean dogState=false;
    private boolean phoneState=false;

    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	devices.put("coffee", false);
    	devices.put("light", false);
    	devices.put("window", false);
    	
    	VerticalLayout topLayout = new VerticalLayout();
    	topLayout.setSizeFull();
    	setContent(topLayout);
    	
    	HorizontalLayout mainLayout = new HorizontalLayout();
    	topLayout.addComponent(mainLayout);
    	topLayout.setComponentAlignment(mainLayout, Alignment.MIDDLE_CENTER);
    	
    	
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        layout.setSpacing(true);
        mainLayout.addComponent(layout);
        
        // Find the application directory
        String basepath = VaadinService.getCurrent()
                          .getBaseDirectory().getAbsolutePath();

        // Image as a file resource
        alarmOffResource = new FileResource(new File(basepath +
                                "/resources/alarmOff.png"));
        alarmOnResource = new FileResource(new File(basepath +
        "/resources/alarmOn.png"));
        FileResource dogOffResource = new FileResource(new File(basepath +
        "/resources/dogOff.png"));
        FileResource dogOnResource = new FileResource(new File(basepath +
                        "/resources/dogOn.png"));
        FileResource phoneOffResource = new FileResource(new File(basepath +
        "/resources/phoneOff.png"));
        FileResource phoneOnResource = new FileResource(new File(basepath +
                        "/resources/phoneOn.png"));
		// Image as a file resource
		coffeeOnRes = new FileResource(new java.io.File(basepath
				+ "/resources/coffeeOn.png"));

		coffeeOffRes = new FileResource(new java.io.File(basepath
				+ "/resources/coffeeOff.png"));
		lightOnRes = new FileResource(new java.io.File(basepath
				+ "/resources/lightOn.png"));

		lightOffRes = new FileResource(new java.io.File(basepath
				+ "/resources/lightOff.png"));
		windowOnRes = new FileResource(new java.io.File(basepath
				+ "/resources/windowOn.png"));

		windowOffRes = new FileResource(new java.io.File(basepath
				+ "/resources/windowOff.png"));
        
        Button pictureButton = new NativeButton();
        pictureButton.setIcon(alarmOffResource);
        layout.addComponent(pictureButton);
        
        pictureButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	if (alarmState==false){
            		pictureButton.setIcon(alarmOnResource);
            		alarmState=true;
            		
            		}
            	else{
            			pictureButton.setIcon(alarmOffResource);
            			alarmState=false;
            		}
            	traverseAllcolumns();
            }
        });
        
        Button dogButton = new NativeButton();
       dogButton.setIcon(dogOffResource);
       layout.addComponent(dogButton);
       dogButton.addClickListener(new Button.ClickListener() {

               @Override
               public void buttonClick(ClickEvent event) {
            	   if (!dogState){
            		   dogButton.setIcon(dogOnResource);
            		   dogState = true;
            		   
            		   
            	   }
                   else
                   {
                	   dogButton.setIcon(dogOffResource);
                	   dogState = false;
                   }
            	   traverseAllcolumns();
               }
       });


       Button phoneButton = new NativeButton();
       phoneButton.setIcon(phoneOffResource);
       layout.addComponent(phoneButton);
       phoneButton.addClickListener(new Button.ClickListener() {

               @Override
               public void buttonClick(ClickEvent event) {
                       if (phoneState==false){
                       phoneButton.setIcon(phoneOnResource);
                       phoneState=true;
                       
                       }
                       else
                       {
                               phoneButton.setIcon(phoneOffResource);
                               phoneState=false;
                       }
                       traverseAllcolumns();
               }
       });
       
       VerticalLayout tableLayout = new VerticalLayout();
       tableLayout.setMargin(true);
       tableLayout.setSpacing(true);
       mainLayout.addComponent(tableLayout);
       
       table = new Table();
       table.setPageLength(0);
       
       table.addContainerProperty(" ", String.class, null);
       table.addContainerProperty("coffee", CheckBox.class, null);
       table.addContainerProperty("light", CheckBox.class, null);
       table.addContainerProperty("window", CheckBox.class, null);
       
       String [] itemIds = {"alarm", "dog", "phone"};
       
       for(int i = 0; i < 3; i++ ){
    	   
    	   CheckBox coffee = new CheckBox();
    	   coffee.setImmediate(true);
    	   coffee.addValueChangeListener(ValueChangeEvent -> traverseAllcolumns());
    	   
    	   CheckBox light = new CheckBox();
    	   light.setImmediate(true);
    	   light.addValueChangeListener(ValueChangeEvent -> traverseAllcolumns());
    	   
    	   CheckBox windo = new CheckBox();
    	   windo.setImmediate(true);
    	   windo.addValueChangeListener(ValueChangeEvent -> traverseAllcolumns());
		   
    	   table.addItem(new Object [] {itemIds[i], coffee, light, windo}, itemIds[i]);
       }
       
       tableLayout.addComponent(table);
       
		// Show the image in the application
		//Image coffeeOff = new Image("", coffeeOffRes);
		//Image lightsOff = new Image("", lightsOffRes);
		//Image windowOff = new Image("", windowOffRes);
       
	    VerticalLayout results = new VerticalLayout();
	    results.setMargin(true);
	    results.setSpacing(true);
	    mainLayout.addComponent(results);
		
	    
		coffeButton = new NativeButton();
		coffeButton.setIcon(coffeeOffRes);
		results.addComponent(coffeButton);


		lightsButton = new NativeButton();
		lightsButton.setIcon(lightOffRes);
		results.addComponent(lightsButton);


		windowButton = new NativeButton();
		windowButton.setIcon(windowOffRes);
		results.addComponent(windowButton);
       
    }
    
    
    private void traverseAllcolumns(){
    	
    	String [] columnIds = {"coffee", "light", "window"};
    	
    	for(int i = 0; i < 3; i++){
    		updateDeviceStatus(columnIds[i]);
    	}
    	
		if ((boolean)devices.get("coffee")) {
			coffeButton.setIcon(coffeeOnRes);
		} else {
			coffeButton.setIcon(coffeeOffRes);
		}
		
		if ((boolean)devices.get("light")) {
			lightsButton.setIcon(lightOnRes);
		} else {
			lightsButton.setIcon(lightOffRes);
		}
		
		if ((boolean)devices.get("window")) {
			windowButton.setIcon(windowOnRes);
		} else {
			windowButton.setIcon(windowOffRes);
		}
    	
    	
    	System.out.println("Coffee: " + devices.get("coffee"));
    	System.out.println("light: " + devices.get("light"));
    	System.out.println("window: " + devices.get("window"));
    }
    
    private void updateDeviceStatus(String columnId) {
    	
    	CheckBox alarmChB = (CheckBox) table.getContainerProperty("alarm", columnId).getValue();
    	CheckBox dogChB = (CheckBox) table.getContainerProperty("dog", columnId).getValue();
    	CheckBox phoneChB = (CheckBox) table.getContainerProperty("phone", columnId).getValue();
    	
    	if(alarmState && dogState && phoneState) {
        	if(alarmChB.getValue() || dogChB.getValue() || phoneChB.getValue()){
        		devices.put(columnId, true);
        	} else {
        		devices.put(columnId, false);
        	}
    	} else if(alarmState && dogState) {
        	if(alarmChB.getValue() || dogChB.getValue()){
        		devices.put(columnId, true);
        	} else {
        		devices.put(columnId, false);
        	}
    	} else if(alarmState && phoneState) {
        	if(alarmChB.getValue() || phoneChB.getValue()){
        		devices.put(columnId, true);
        	} else {
        		devices.put(columnId, false);
        	}
    	} else if(phoneState && dogState) {
        	if(phoneChB.getValue() || dogChB.getValue()){
        		devices.put(columnId, true);
        	} else {
        		devices.put(columnId, false);
        	}
    	} else if(alarmState) {
        	if(alarmChB.getValue()){
        		devices.put(columnId, true);
        	} else {
        		devices.put(columnId, false);
        	}
    	} else if(dogState) {
        	if(dogChB.getValue()){
        		devices.put(columnId, true);
        	} else {
        		devices.put(columnId, false);
        	}
    	} else if(phoneState) {
        	if(phoneChB.getValue()){
        		devices.put(columnId, true);
        	} else {
        		devices.put(columnId, false);
        	}
    	} else {
    		devices.put(columnId, false);
    	}
    }
    

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
