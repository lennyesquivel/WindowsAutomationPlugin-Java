package xyz.lennyesquivel.models;

import xyz.lennyesquivel.models.enums.By;
import xyz.lennyesquivel.models.enums.ElementType;

public class WinElement {

    public By byLocator;
    public String locatorValue;
    public Object nativeElement;
    public ElementType type;

    public WinElement() {

    }

    /**
     * Click on Windows element.
     * driver-attached-action: ClickOnElement
     * @author Lenny Esquivel
     */
    public void click() {

    }

    public void doubleClick() {

    }

    public void rightClick() {

    }

    public void rightDoubleClick() {

    }

    public void type() {

    }

    public void highlight() {

    }

}
