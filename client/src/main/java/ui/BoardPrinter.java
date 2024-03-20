package ui;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;

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
            chessBoard.append(edgeBGColor + edgeTextColor);
            chessBoard.append(EMPTY);
            chessBoard.append(SET_BG_COLOR_RED);
            for(int c = 1; c <= 8; c++){
                if(board[r][c] == null){
                    chessBoard.append(EMPTY);
                } else {
                    ChessPiece.PieceType type = board[r][c].getPieceType();
                    ChessGame.TeamColor color = board[r][c].getTeamColor();
                    chessBoard.append(printPiece(type, color));
                }
            }
            chessBoard.append(edgeBGColor + edgeTextColor);
            chessBoard.append(EMPTY);
            chessBoard.append(advanceLine(out));
        }


        drawChessBoard(out, chessBoard.toString());


    }

    private static void drawChessBoard(PrintStream out, String chessBoard){
        setEdgeFormat(out);
        out.print(EMPTY + " A  B  C  D  E  F  G  H " + EMPTY);
        out.print(advanceLine(out));
        out.print(chessBoard);
    }

    private static void setEdgeFormat(PrintStream out){
        out.print(edgeBGColor + edgeTextColor);
    }
    private static String advanceLine(PrintStream out){
        return abyssColor + "\n";
    }

    private static String printPiece(ChessPiece.PieceType type, ChessGame.TeamColor color){
        if(color == ChessGame.TeamColor.BLACK){
            switch(type) {
                case ROOK:
                    return BLACK_ROOK;
                case KNIGHT:
                    return BLACK_KNIGHT;
                case BISHOP:
                    return BLACK_BISHOP;
                case ChessPiece.PieceType.QUEEN:
                    return BLACK_QUEEN;
                case KING:
                    return BLACK_KING;
                case PAWN:
                    return BLACK_PAWN;
                default:
                    return " X ";
            }
        } else {
            switch(type){
                case ROOK:
                    return WHITE_ROOK;
                case KNIGHT:
                    return WHITE_KNIGHT;
                case BISHOP:
                    return WHITE_BISHOP;
                case QUEEN:
                    return WHITE_QUEEN;
                case KING:
                    return WHITE_KING;
                case PAWN:
                    return WHITE_PAWN;
                default:
                    return " X ";
            }
        }
    }


}
