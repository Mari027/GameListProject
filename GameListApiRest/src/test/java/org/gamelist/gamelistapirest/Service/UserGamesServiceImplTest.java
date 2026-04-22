package org.gamelist.gamelistapirest.Service;

import org.gamelist.gamelistapirest.Mapper.GameMapper;
import org.gamelist.gamelistapirest.Mapper.UserGamesMapper;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.gamelist.gamelistapirest.Repository.UserGamesRepository;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.gamelist.gamelistapirest.Service.ExternalGamesService.ExternalGameService;
import org.gamelist.gamelistapirest.Service.UserGamesService.UserGamesService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserGamesServiceImplTest {

    @Mock
    private  UserGamesRepository userGamesRepository;

    @Mock
    private  UserRepository userRepository;

    @Mock
    private  GamesRepository gameRepository;

    @Mock
    private  ExternalGameService externalGameService;

    @Mock
    private  UserGamesMapper userGamesMapper;

    @Mock
    private  GameMapper gameMapper;

    @InjectMocks
    private UserGamesService userGamesService;
}
