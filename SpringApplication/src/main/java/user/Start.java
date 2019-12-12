package user;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import configuration.ApplicationConfig;
import service.CRUDContainer;

public class Start {
	public static Scanner scanner = new Scanner(System.in);

	public static int exitSign() {
		System.out.println("If u want to continue press 0  , else any other key for going one step back");
		int sign = scanner.nextInt();
		return sign;
	}
//	@Autowired
//	@Qualifier("EMSManagement")
//	private static CRUDContainer emsManagement;
//	@Qualifier("CMSManagement")
//	@Autowired
//	private static CRUDContainer cmsManagement;

	public static void main(String[] args) {
		int sign;
		do {
			System.out.println("||##############################||");
			System.out.println("||        Enter 1 for EMS       ||");
			System.out.println("||        Enter 2 for CMS       ||");
			System.out.println("||        Enter 3 for Exit      ||");
			System.out.println("||##############################||");
			System.out.println("Enter your choice ");
			sign = 1;
			try {
				int choiceInInteger = scanner.nextInt();
				switch (choiceInInteger) {
				case 1:
					choiceCasesOfCruds(getObjectOfCRUDContainer("EMSManagement"));
					//choiceCasesOfCruds(emsManagement);
					sign = exitSign();
					break;
				case 2:
					choiceCasesOfCruds(getObjectOfCRUDContainer("CMSManagement"));
					//choiceCasesOfCruds(cmsManagement);
					sign = exitSign();
					break;
				case 3:
					sign = 1;
					break;
				default:
					System.out.println("Please choose from given options");
					sign = 0;
				}
			} catch (Exception exception) {
				System.out.println("Please enter only integers");
				scanner.next();
				sign = 0;
			}
		} while (sign == 0);
		System.out.println("Your program has been successfully terminated..........");

	}

	public static void choiceCasesOfCruds(CRUDContainer object) {
		int sign;
		do {
			String managementSystemName = object.getClass().getSimpleName().equals("EMSManagement")
					? "Welcome you are in EMS"
					: "Welcome you are in CMS";
			System.out.println("############################################");
			System.out.println("||        " + managementSystemName + "          ||");
			System.out.println("############################################");
			System.out.println("||        Enter 1 for Add an entry        ||");
			System.out.println("||        Enter 2 for Read an entry       ||");
			System.out.println("||        Enter 3 view all entries        ||");
			System.out.println("||        Enter 4 for Update an entry     ||");
			System.out.println("||        Enter 5 for Delete an entry     ||");
			System.out.println("||        Enter 6 for exit                ||");
			System.out.println("############################################");
			sign = 1;
			System.out.println("Enter Your Choice");
			try {
				int choiceInInteger = scanner.nextInt();
				switch (choiceInInteger) {
				case 1:
					object.add();
					sign = exitSign();
					break;
				case 2:
					object.read();
					sign = exitSign();
					break;
				case 3:
					object.readall();
					sign = exitSign();
					break;
				case 4:
					object.update();
					sign = exitSign();
					break;
				case 5:
					object.delete();
					sign = exitSign();
					break;
				case 6:
					sign = 1;
					System.out.println("If u want to choose any management system press 0 else any other key for exit");
					break;
				default:
					System.out.println("Please Choose from above six options");
					sign = 0;

				}
			} catch (Exception exception) {
				System.out.println("Please enter only integers");
				scanner.next();
				sign = 0;
			}

		} while (sign == 0);
	}

	public static CRUDContainer getObjectOfCRUDContainer(String className) {
		ApplicationContext ctx = new AnnotationConfigApplicationContext(ApplicationConfig.class);
		CRUDContainer objectOfCRUDContainer = (CRUDContainer) ctx.getBean(className);
		return objectOfCRUDContainer;
	}

}
