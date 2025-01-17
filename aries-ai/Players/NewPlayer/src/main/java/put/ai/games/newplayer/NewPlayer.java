/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package put.ai.games.newplayer;

import java.util.List;
import java.util.Random;
import put.ai.games.game.Board;
import put.ai.games.game.Move;
import put.ai.games.game.Player;
import put.ai.games.game.moves.MoveMove;

public class NewPlayer extends Player {

    private Random random = new Random(0xdeadbeef);

    private Move priorityMove(Board b) {
        List<Move> moves = b.getMovesFor(getColor());

        // czy ruch wygrywajacy tzn do rogu przeciwnika
        for(Move move: moves) {
            int dstX = ((MoveMove) move).getDstX();
            int dstY = ((MoveMove) move).getDstY();

            if(getColor() == Color.PLAYER2 && dstY == 0 && dstX == 0) {
                return move;
            } else if(getColor() == Color.PLAYER1 && dstY == b.getSize()-1 && dstX == b.getSize()-1) {
                return move;
            }
        }

        // czy ruch zgniata zmiata przeciwnika z planszy czy zgniata o naszego
        for(Move move: moves) {
            int dstX = ((MoveMove) move).getDstX();
            int dstY = ((MoveMove) move).getDstY();
            int srcX = ((MoveMove) move).getSrcX();
            int srcY = ((MoveMove) move).getSrcY();

            //najpierw czy przesuwa przeciwnika
            if(b.getState(dstX,dstY) != getOpponent(getColor())) {
                continue;
            }

            //okreslamy kierunek; wektor przesuniecia
            int dirX = dstX - srcX;
            if(dirX > 0){
                dirX = 1; // w prawo
            } else if(dirX < 0) {
                dirX = -1; // w lewo
            }

            int dirY = dstY - srcY;
            if(dirY > 0) {
                dirY = 1; // w dol
            } else if(dirY < 0) {
                dirY = -1; // w gore
            }

            // czy zmiata z planszy
            int nextX = dstX + dirX;
            int nextY = dstY + dirY;
            if(nextX < 0 || nextY < 0 || nextX >= b.getSize() || nextY >= b.getSize()) {
                return move;
            }

            // teraz sprawdzamy czy miazdzymy o naszego bezposrednio lub przesuwamy przeciwnika i miazdzymy o naszego
            nextX = dstX;
            nextY = dstY;
            while(true)
            {
                nextX += dirX;
                nextY += dirY;
                if(b.getState(nextX, nextY) == getColor()){
                    return move;
                } else if(b.getState(nextX, nextY) == getOpponent(getColor())) {
                    continue;
                }
                break;
            }

        }
        return moves.get(random.nextInt(moves.size()));
    }

    private int evaluate(Board b) {
        int diff=0;

        for (int x=0; x<b.getSize(); x++){
            for (int y=0; y<b.getSize(); y++) {
                if (b.getState(x, y) == getColor()) {  // Ten sam kolor co mój
                    diff += 1;
                } else if (b.getState(x, y) == Color.EMPTY) {  // Puste pole
                    continue;
                } else {  // Inny kolor niż mój
                    diff -= 1;
                }
            }
        }
        return diff;
    }

    public class Result {  // klasa, która pozwala mi przechowywac dwie wartości w dunkcji minimax
        private Move move;  // ruch
        private int value;  // wartość

        public void setMove(Move m) { this.move = m; }

        public void setValue(int v) { this.value = v; }

        public Move getMove() { return this.move; }

        public int getValue() { return this.value; }

        public boolean isGreater(Result r) {
            return this.getValue() > r.getValue();
        }

        public boolean isSmaller(Result r) {
            return this.getValue() < r.getValue();
        }
    }

    private Result minMax(Board b, int depth, boolean max_player) {
        Result result = new Result(), bestResult = new Result();

        if (depth == 0 || b.getWinner(getColor()) != null) {
            int winner_prize = 0;  // zmienna, ktora oznacza bonus wygranej
            Color opponent_color = getColor() == Color.PLAYER1 ? Color.PLAYER2 : Color.PLAYER1;  // okresl kolor przeciwnika

            if (max_player) {  // moja tura
                if (b.getWinner(getColor()) == getColor()) {  // jezeli w nastepnym ruchu wygram to bonus
                    winner_prize = 64;
                } else if (b.getWinner(getColor()) == opponent_color){  // jezeli w nastepnym ruchu przegram to kara
                    winner_prize = -64;
                }
            } else { // tura przeciwnika
                if (b.getWinner(opponent_color) == opponent_color){  // zwycieza przeciwnik - bonus dla niego (kara dla mnie)
                    winner_prize = -64;
                } else if (b.getWinner(opponent_color) == getColor()) { // zwyciezam ja - kara dla niego (bonus dla mnie)
                    winner_prize = 64;
                }
            }
            result.setValue(evaluate(b) + winner_prize); // ustaw wartosc gry na liczba roznicy pionkow + ewentualny bonus/kara

            return result;
        }

        if (max_player) {  // moja tura
            List<Move> moves = b.getMovesFor(getColor());
            bestResult.setValue(-64);

            for(Move move: moves) {
                b.doMove(move);  // zasymuluj ruch
                result = minMax(b, depth-1, false);
                if (result.isGreater(bestResult)) {
                    bestResult.setValue(result.getValue());  // poprawiony wynik
                    bestResult.setMove(move);  // poprawiony ruch
                }
                b.undoMove(move);  // wroc do stanu przed tym ruchem
            }
            return bestResult;

        } else {  // tura przeciwnika
            Color opponent_color = getColor() == Color.PLAYER1 ? Color.PLAYER2 : Color.PLAYER1;  // okresl kolor przeciwnika
            List<Move> moves = b.getMovesFor(opponent_color);
            bestResult.setValue(64);

            for (Move move : moves) {
                b.doMove(move);  // zasymuluj ruch
                result = minMax(b, depth-1, true);
                if (result.isSmaller(bestResult)){
                    bestResult.setValue(result.getValue());  // poprawiony wynik
                    bestResult.setMove(move);  // poprawiony ruch
                }
                b.undoMove(move);  // wroc do stanu przed tym ruchem
            }
            return bestResult;
        }
    }

    private Result alfaBeta(Board b, int depth, int alpha, int beta, boolean max_player) {
        Result result = new Result(), bestResult = new Result();
        Color opponent_color = getColor() == Color.PLAYER1 ? Color.PLAYER2 : Color.PLAYER1;  // okresl kolor przeciwnika

        // if (depth == 0 || b.getWinner(getColor()) != null) { to bylo wczesniej
        if (depth == 0 || max_player && b.getWinner(getColor()) != null || !max_player && b.getWinner(opponent_color) != null) {  // DO ANALIZY
            int winner_prize = 0;  // zmienna, ktora oznacza bonus wygranej

            if (max_player) {  // moja tura
                if (b.getWinner(getColor()) == getColor()) {  // jezeli w nastepnym ruchu wygram to bonus
                    winner_prize = 64;
                } else if (b.getWinner(getColor()) == opponent_color){  // jezeli w nastepnym ruchu przegram to kara
                    winner_prize = -64;
                }
            } else { // tura przeciwnika
                if (b.getWinner(opponent_color) == opponent_color){  // zwycieza przeciwnik - bonus dla niego (kara dla mnie)
                    winner_prize = -64;
                } else if (b.getWinner(opponent_color) == getColor()) { // zwyciezam ja - kara dla niego (bonus dla mnie)
                    winner_prize = 64;
                }
            }
            result.setValue(evaluate(b) + winner_prize); // ustaw wartosc gry na liczba roznicy pionkow + ewentualny bonus/kara

            return result;
        }

        if (max_player) {  // moja tura
            List<Move> moves = b.getMovesFor(getColor());

            for(Move move: moves) {
                b.doMove(move);  // zasymuluj ruch
                result = minMax(b, depth-1, alpha, beta, false);
                b.undoMove(move);  // wroc do stanu przed tym ruchem
                alpha = Math.max(result.getValue(), alpha);
                bestResult.setMove(move);
                if (alpha >= beta) {
                    bestResult.setValue(beta);  // poprawiony wynik
                    bestResult.setMove(move);  // poprawiony ruch
                    return bestResult;
                }
            }

            bestResult.setValue(alpha);
            return bestResult;

        } else {  // tura przeciwnika
            List<Move> moves = b.getMovesFor(opponent_color);

            for (Move move : moves) {
                b.doMove(move);  // zasymuluj ruch
                result = minMax(b, depth-1, alpha, beta, true);
                b.undoMove(move);  // wroc do stanu przed tym ruchem
                beta = Math.min(result.getValue(), beta);
                bestResult.setMove(move);
                if (alpha >= beta){
                    bestResult.setValue(alpha);  // poprawiony wynik
                    bestResult.setMove(move);  // poprawiony ruch
                    return bestResult;
                }
            }

            bestResult.setValue(beta);
            return bestResult;
        }
    }

    @Override
    public String getName() {
        return "Jakub Kaczmarek 145291 Andrzej Kapczyński 145358";
    }


    @Override
    public Move nextMove(Board b) { // plansza - x rosnie w prawo [0, size-1], y rosnie w dol [0, size-1]
        return minMax(b, 2, true).getMove();
//        return priorityMove(b);
    }
}
