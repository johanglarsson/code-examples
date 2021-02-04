package com.samples.springgraphqlservice.games;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.annotations.GraphQLQuery;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@GraphQLApi
@RequiredArgsConstructor
public class GameService {

  private final GameRepository gameRepository;

  @GraphQLQuery(name = "games")
  public Iterable<Game> getGames() {
    return gameRepository.findAll();
  }

  @GraphQLQuery(name = "game")
  public Optional<Game> getGame(@GraphQLArgument(name = "id") Long id) {
    String test = "test";

    return gameRepository.findById(id);
  }

  @GraphQLMutation(name = "saveGame")
  public Game saveGame(
      @GraphQLArgument(name = "game") String name,
      @GraphQLArgument(name = "company") String company,
      @GraphQLArgument(name = "devices") List<DeviceName> devices) {
    Game game = new Game();
    game.setCompanyName(company);
    game.setName(name);
    devices.forEach(game::addDevice);
    return gameRepository.save(game);
  }
}
