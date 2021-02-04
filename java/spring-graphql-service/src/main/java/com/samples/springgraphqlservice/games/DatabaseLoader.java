package com.samples.springgraphqlservice.games;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DatabaseLoader implements CommandLineRunner {

  private final GameRepository gameRepository;

  @Override
  public void run(String... args) throws Exception {
    log.info("Creating game data samples");
    createGame(List.of(DeviceName.PS4, DeviceName.PS5), "Last of us", "Naughty Dog");
    createGame(List.of(DeviceName.PS4), "Assasins Creed Valhalla", "Ubisoft");
    log.info("Samples created in database");
  }

  private void createGame(List<DeviceName> deviceNames, String name, String company) {
    Game game = new Game();
    game.setName(name);
    game.setCompanyName(company);
    deviceNames.stream().forEach(game::addDevice);
    gameRepository.save(game);
  }
}
