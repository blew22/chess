package ui;

import chess.ChessBoard;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

import static ui.EscapeSequences.*;

public class BoardPrinter {

    private static final String edgeBGColor = SET_BG_COLOR_LIGHT_GREY;
    private static final String edgeTextColor = SET_TEXT_COLOR_BLACK;
    private static final String abyssColor = SET_BG_COLOR_WHITE;

    public static void printUI(chess.ChessPiece[][] board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        StringBuilder chessBoard = new StringBuilder();
        for(int r = 8; r > 0 ; r--){
            for(int c = 1; c <= 8; c++){
                if(board[r][c] == null){
                    chessBoard.append(EMPTY);
                } else {
                    chessBoard.append(board[r][c].toString());
                }
            }
            chessBoard.append("\n");
        }


        drawChessBoard(out, chessBoard.toString());


    }

    private static void drawChessBoard(PrintStream out, String chessBoard){
        setEdgeFormat(out);
        out.print("    a  b  c  d  e  f  g  h    ");
        advanceLine(out);
    }

    private static void setEdgeFormat(PrintStream out){
        out.print(edgeBGColor + edgeTextColor);
    }
    private static void advanceLine(PrintStream out){
        out.print(abyssColor + "\n");
    }


}
