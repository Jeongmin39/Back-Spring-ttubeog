package com.ttubeog.domain.game.domain.repository;

import com.ttubeog.domain.benefit.domain.Benefit;
import com.ttubeog.domain.game.domain.Game;
import com.ttubeog.domain.game.domain.GameType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

//    Boolean existsByBenefitAndType(Benefit benefit, GameType type);

//    Optional<Game> findByBenefit(Benefit benefit);
}
