package de.morrisbr.witzlecraft.main;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.mongodb.client.MongoDatabase;
import de.morrisbr.mastereco.account.AccountService;
import de.morrisbr.verify.mongodb.MongoManager;
import de.morrisbr.verify.network.objects.Account;
import de.morrisbr.witzlecraft.network.events.VerifyEvent;
import de.morrisbr.witzlecraft.network.objects.EventPlayer;
import de.morrisbr.witzlecraft.pages.RegisterPage;
import de.morrisbr.witzlecraft.network.Network;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import io.javalin.Javalin;
import io.javalin.http.staticfiles.Location;
import io.javalin.plugin.rendering.template.JavalinJte;
import services.VerifyService;

public class Main {
	
	public static HashMap<String, VerifyEvent> queueEvents = new HashMap<String, VerifyEvent>();
	private static MongoManager mongoManager = new MongoManager();
	public static MongoDatabase mongoDatabase = mongoManager.getDatabase();

    public static void main(String[] args) {



		Server server = new Server();
		Network.register(server);
		try {
			server.bind(Network.port, 54777);
		} catch (IOException e) {
			e.printStackTrace();
		}
		server.start();



		server.addListener(new Listener() {
			public void received (Connection connection, Object object) {

				if(object instanceof VerifyEvent)
				{
					VerifyEvent event = ((VerifyEvent) object);
					System.out.println(((VerifyEvent) object).getCode());
					System.out.println(object.getClass().getName());
					queueEvents.put(event.getCode(), event);
				}

			}

			public void connected(Connection connection) {
			}
		});


		
		
        Javalin app = Javalin.create().start(80);

		app.config.addStaticFiles("resources/OnlineBanking", Location.EXTERNAL);

		//app.config.addStaticFiles("C:/Users/MorrisBrandt/Desktop/Test", Location.EXTERNAL);

        DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Paths.get("resources/OnlineBanking"));
        TemplateEngine engine = TemplateEngine.create(codeResolver, ContentType.Html);
        engine.createPrecompiled(Path.of("jte-classes"), ContentType.Html);
        JavalinJte.configure(engine);

        
        app.get("/", ctx -> {
        	ctx.render("index.jte");
        });

		app.get("/banking", ctx -> {
			ctx.render("login.jte");
		});

		app.get("/login", ctx -> {
			RegisterPage page = new RegisterPage();
			page.setCode("443w53453465436456");
			ctx.render("login.jte", Collections.singletonMap("page", page));
		});

		app.post("/sendlogin", ctx -> {

			VerifyService verifyService = new VerifyService(mongoManager);

			System.out.println(ctx.formParam("name"));
			System.out.println(ctx.formParam("password"));
			if(verifyService.isLoginValid(ctx.formParam("name"), ctx.formParam("password"))) {
				Account account = verifyService.getAccount(ctx.formParam("name"));

				ctx.result(account.getUsername() + "\n" + account.getPassword() + "\n" + account.getUuid());
			} else {
				ctx.result("incorect");
			}

		});

		app.get("/register", ctx -> {
			ctx.render("register.jte");
		});
		
		
		app.get("/verify", ctx -> {
			if(queueEvents.get(ctx.queryParam("key")) != null) {
				VerifyEvent event = queueEvents.get(ctx.queryParam("key"));
				VerifyService service = new VerifyService(mongoManager);

				if(!(service.getAccountDocument(event.getPlayer().getName()) == null)) {
					ctx.html(event.getPlayer().getName() + " ist bereits registriert!");
					return;
				}

				RegisterPage page = new RegisterPage();
		        page.setCode(event.getCode());
		        page.setPlayer(event.getPlayer());
				ctx.render("verify.jte", Collections.singletonMap("page", page));
//				ctx.html(event.getPlayer().getName() + "<br>" 
//				+  event.getPlayer().getUuid() + "<br>"
//			    + AccountService.getPlayerAccount(UUID.fromString(event.getPlayer().getUuid())).getBankMoney());
			} else {
				ctx.html("Code is not exist!");
			}
		});
		
		app.post("/verifyregister", ctx -> {
			//ctx.html(ctx.formParam("password") + " f");
			if(queueEvents.containsKey(ctx.queryParam("key"))) {

				VerifyService service = new VerifyService(mongoManager);
				VerifyEvent event = queueEvents.get(ctx.queryParam("key"));

					event.sucess = true;
					service.registerAccount(event.getPlayer(), ctx.formParam("password"));

					RegisterPage page = new RegisterPage();
					page.setCode(event.getCode());
					page.setPlayer(event.getPlayer());

					server.sendToAllTCP(event);

					ctx.render("welcome.jte", Collections.singletonMap("page", page));

			} else {
				ctx.html("Code is not exist!");
			}
			queueEvents.remove(ctx.queryParam("key"));
		});

        app.post("/money-transfer", ctx -> {
        	if(AccountService.hasUserAccount(UUID.fromString(ctx.formParam("uuid")))) {
        		AccountService.getPlayerAccount(UUID.fromString(ctx.formParam("uuid"))).giveBankAccMoney(Long.parseLong(ctx.formParam("money")));
        		ctx.html(ctx.formParam("money") + " has been sended!");
        		//server.sendToAllTCP("§a" + ctx.formParam("uuid") + " wurden " + ctx.formParam("money") + "$ überwiesen!");
				//server.sendToAllTCP();
        	} else {
        		ctx.html(ctx.formParam("uuid") + " ist leider nicht in unsere Datenbank registriert.");
        	}
        });
        
    }

}
