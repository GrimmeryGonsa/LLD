import controller.ConsoleGameController;
import repository.GameRepository;
import repository.GameStateRepository;
import service.GameManagementService;

public class Main
{

    public static void main(String[] args)
    {
        GameRepository repo = new GameStateRepository();
        GameManagementService service = new GameManagementService(repo);
        ConsoleGameController controller = new ConsoleGameController(service);
        controller.run();

    }
}
