package com.markyang.framework.config.banner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务启动Banner打印类
 *
 * @author yangchangliang
 * @version 1
 */
@Component
@Order
public class ServiceStartedBannerPrinterRunner implements ApplicationRunner {
    /**
     * Callback used to run the bean.
     *
     * @param args incoming application arguments
     * @throws Exception on error
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(
            AnsiOutput.toString(AnsiColor.BRIGHT_BLUE,
                "\n" +
                    "  _____                 _             _____ _             _           _    _____                              __       _ _       \n" +
                    " / ____|               (_)           / ____| |           | |         | |  / ____|                            / _|     | | |      \n" +
                    "| (___   ___ _ ____   ___  ___ ___  | (___ | |_ __ _ _ __| |_ ___  __| | | (___  _   _  ___ ___ ___  ___ ___| |_ _   _| | |_   _ \n" +
                    " \\___ \\ / _ \\ '__\\ \\ / / |/ __/ _ \\  \\___ \\| __/ _` | '__| __/ _ \\/ _` |  \\___ \\| | | |/ __/ __/ _ \\/ __/ __|  _| | | | | | | | |\n" +
                    " ____) |  __/ |   \\ V /| | (_|  __/  ____) | || (_| | |  | ||  __/ (_| |  ____) | |_| | (_| (_|  __/\\__ \\__ \\ | | |_| | | | |_| |\n" +
                    "|_____/ \\___|_|    \\_/ |_|\\___\\___| |_____/ \\__\\__,_|_|   \\__\\___|\\__,_| |_____/ \\__,_|\\___\\___\\___||___/___/_|  \\__,_|_|_|\\__, |\n" +
                    "                                                                                                                            __/ |\n" +
                    "                                                                                                                           |___/ "
            )
        );
    }
}
