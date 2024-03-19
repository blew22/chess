import chess.*;
import ui.BoardPrinter;

public class Main {

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

        chess.ChessPiece[][] board = new ChessBoard().getBoard();
        BoardPrinter.printUI(board);

    }
}