package ui;

import chess.ChessGame;
import chess.ChessPiece;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static ui.EscapeSequences.*;

public class BoardPrinter {

    private static final String edgeBGColor = SET_BG_COLOR_BLUE;
    private static final String edgeTextColor = SET_TEXT_COLOR_BLACK;
    private static final String abyssColor = "\u001B[49m";
    private static final String lightSquareBGColor = SET_BG_COLOR_WHITE;
    private static final String lightSquareTextColor = SET_TEXT_COLOR_BLACK;
    private static final String darkSquareBGColor = SET_BG_COLOR_LIGHT_GREY;
    private static final String darkSquareTextColor = SET_TEXT_COLOR_WHITE;
    private static final String newLine = abyssColor + "\n";


    public static void printUI(chess.ChessPiece[][] board) {
        var out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        out.print(ERASE_SCREEN);

        String whiteBoard = whiteBoardToString(board);
        String blackBoard = blackBoardToString(board);

        drawWhiteChessBoard(out, whiteBoard);
        out.print(newLine);
        drawBlackChessBoard(out, blackBoard);
        out.print(newLine);


    }

    private static String whiteBoardToString(ChessPiece[][] board) {
        StringBuilder chessBoard = new StringBuilder();
        for (int r = 8; r > 0; r--) {
            chessBoard.append(edgeBGColor + edgeTextColor);
            chessBoard.append("\u2003" + r + " ");
            for (int c = 1; c <= 8; c++) {
                if ((isEven(r) && isEven(c)) || (!isEven(r) && !isEven(c))) {
                    chessBoard.append(darkSquareBGColor);
                } else {
                    chessBoard.append(lightSquareBGColor);
                }
                if (board[r][c] == null) {
                    chessBoard.append(EMPTY);
                } else {
                    ChessPiece.PieceType type = board[r][c].getPieceType();
                    ChessGame.TeamColor color = board[r][c].getTeamColor();
                    chessBoard.append(printPiece(type, color));
                }
            }
            chessBoard.append(edgeBGColor + edgeTextColor);
            chessBoard.append(" " + r + "\u2003");
            chessBoard.append(newLine);
        }
        return chessBoard.toString();
    }

    private static String blackBoardToString(ChessPiece[][] board) {
        StringBuilder chessBoard = new StringBuilder();
        for (int r = 1; r <= 8; r++) {
            chessBoard.append(edgeBGColor + edgeTextColor);
            chessBoard.append("\u2003" + r + " ");
            for (int c = 8; c >= 1; c--) {
                if ((isEven(r) && isEven(c)) || (!isEven(r) && !isEven(c))) {
                    chessBoard.append(darkSquareBGColor);
                } else {
                    chessBoard.append(lightSquareBGColor);
                }
                if (board[r][c] == null) {
                    chessBoard.append(EMPTY);
                } else {
                    ChessPiece.PieceType type = board[r][c].getPieceType();
                    ChessGame.TeamColor color = board[r][c].getTeamColor();
                    chessBoard.append(printPiece(type, color));
                }
            }
            chessBoard.append(edgeBGColor + edgeTextColor);
            chessBoard.append(" " + r + "\u2003");
            chessBoard.append(newLine);
        }
        return chessBoard.toString();
    }

    private static void drawWhiteChessBoard(PrintStream out, String chessBoard){
        setEdgeFormat(out);
        out.print(EMPTY + "\u2003A \u2003B \u2003C \u2003D \u2003E \u2003F \u2003G \u2003H " + EMPTY);
        out.print(newLine);
        out.print(chessBoard);
        setEdgeFormat(out);
        out.print(EMPTY + "\u2003A \u2003B \u2003C \u2003D \u2003E \u2003F \u2003G \u2003H " + EMPTY);
        out.print(newLine);
    }

    private static void drawBlackChessBoard(PrintStream out, String chessBoard){
        setEdgeFormat(out);
        out.print(EMPTY + "\u2003H \u2003G \u2003F \u2003E \u2003D \u2003C \u2003B \u2003A " + EMPTY);
        out.print(newLine);
        out.print(chessBoard);
        setEdgeFormat(out);
        out.print(EMPTY + "\u2003H \u2003G \u2003F \u2003E \u2003D \u2003C \u2003B \u2003A " + EMPTY);
        out.print(newLine);
    }

    private static void setEdgeFormat(PrintStream out){
        out.print(edgeBGColor + edgeTextColor);
    }
//    private static String advanceLine(PrintStream out){
//        return abyssColor + "\n";
//    }

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

    private static boolean isEven(int x){
        return (x%2) == 0;
    }


}
