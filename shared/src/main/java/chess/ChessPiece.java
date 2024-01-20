package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {


    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {

        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        ChessPiece myPiece = board.getPiece(myPosition);
        HashSet<ChessMove> possibleMoves = new HashSet<>();

        if (myPiece.getPieceType() == PieceType.ROOK){
            int currentRow = myPosition.getRow();
            int currentCol = myPosition.getColumn();

            //all positions in row
            //looking right
            for(int i = currentCol + 1; i < 8; i++){
                ChessPosition destination = new ChessPosition(currentRow, i);
                ChessMove move = new ChessMove(myPosition, destination, null);
                if(board.getPiece(destination) != null) break;
                possibleMoves.add(move);
            }
            //looking left
            for(int i = currentCol - 1; i >= 0; i--){
                ChessPosition destination = new ChessPosition(currentRow, i);
                ChessMove move = new ChessMove(myPosition, destination, null);
                //if there is already a piece at the destination, break
                if(board.getPiece(destination) != null) break;
                possibleMoves.add(move);
            }

            //all positions in column
            //looking up
            for(int i = currentRow + 1; i < 8; i++){

                ChessPosition destination = new ChessPosition(i, myPosition.getColumn());
                if(destination == myPosition) continue;
                ChessMove move = new ChessMove(myPosition, destination, null);
                if(board.getPiece(destination) != null) break;
                possibleMoves.add(move);
            }
            //looking down
            for(int i = currentRow - 1; i >= 0; i--){

                ChessPosition destination = new ChessPosition(i, myPosition.getColumn());
                if(destination == myPosition) continue;
                ChessMove move = new ChessMove(myPosition, destination, null);
                if(board.getPiece(destination) != null) break;
                possibleMoves.add(move);
            }
        }
        return possibleMoves;
    }
}
