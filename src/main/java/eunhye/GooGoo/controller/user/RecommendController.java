package eunhye.GooGoo.controller.user;

import org.openqa.selenium.By;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class RecommendController {

    @GetMapping("/recommend")
    public String recommend(Model model){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("headless");

        WebDriver driver = new ChromeDriver(options);

        driver.get("https://play.google.com/store/apps/collection/topselling_free?clp=ChcKFQoPdG9wc2VsbGluZ19mcmVlEAcYAw%3D%3D:S:ANO1ljLwMrI&gsr=ChkKFwoVCg90b3BzZWxsaW5nX2ZyZWUQBxgD:S:ANO1ljIxP20");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        List<WebElement> appList = driver.findElements(By.className("ULeU3b"));
        List<String> imgSrc = new ArrayList<String>();
        List<String> name = new ArrayList<String>();
        List<String> star = new ArrayList<>();
        List<String> installLink = new ArrayList<>();

        for(WebElement element : appList){
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            imgSrc.add(element.findElement(By.className("T75of")).getAttribute("src"));
            name.add(element.findElement(By.className("Epkrse")).getText());
            star.add(element.findElement(By.className("LrNMN")).getText());
            installLink.add(element.findElement(By.className("Si6A0c")).getAttribute("href"));
        }

        driver.quit();
        model.addAttribute("imgSrc", imgSrc);
        model.addAttribute("name", name);
        model.addAttribute("star", star);
        model.addAttribute("installLink", installLink);
        return "/recommend";
    }
}
