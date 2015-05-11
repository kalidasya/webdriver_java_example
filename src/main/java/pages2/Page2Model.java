package pages2;

import org.openqa.selenium.WebDriver;
import osmo.tester.annotation.Guard;
import osmo.tester.annotation.Post;
import osmo.tester.annotation.TestStep;

/**
 * Created by kalidasya on 2015.05.08..
 */
public class Page2Model {


  private final WebDriver driver;

  public Page2Model(WebDriver driver) {
    this.driver = driver;
  }

  @Guard("openLogin")
  public boolean canOpenLogin(){
    try {
      new HomePage(driver).isLoaded();
    } catch (AssertionError e){
      return false;
    }
    return true;
  }

  @TestStep("openLogin")
  public void openLogin() {
    new HomePage(driver).clickOnLogin();
  }

  @Post("openLogin")
  public void checkOpenLogin() {
    new AuthenticationPage(driver).isLoaded();
  }

  @Guard("login")
  public boolean canLogin(){
    AuthenticationPage authPage = new AuthenticationPage(driver);
    try {
      authPage.isLoaded();
    } catch (AssertionError e){
      return false;
    }
    return true;
  }

  @TestStep("login")
  public void login(){
    new AuthenticationPage(driver).loginWith("asdasd", "poaksdposd");
  }

  @Post("login")
  public void checkLogin(){
    new MyAccountPage(driver).isLoaded();
  }
}
