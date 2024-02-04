package chess;

import java.util.*;

import static chess.ChessGame.TeamColor.BLACK;
import static chess.ChessGame.TeamColor.WHITE;

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

    @Override
    public String toString() {
        return "ChessPiece{" +
                "pieceColor=" + pieceColor +
                ", type=" + type +
                '}';
    }

    //    private ChessMove moveHelper(ChessPosition myPosition, int endRow, int endCol, ChessBoard board) {
//        ChessPosition destination = new ChessPosition(endRow, endCol);
//        ChessMove move = new ChessMove(myPosition, destination, null);
//        //check if other pieces are on destination
//        return move;
//    }

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

        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        if (myPiece.getPieceType() == PieceType.ROOK) {
            //all positions in row
            //looking right
            for (int i = currentCol + 1; i <= 8; i++) {
                ChessPosition destination = new ChessPosition(currentRow, i);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }
            //looking left
            for (int i = currentCol - 1; i > 0; i--) {
                ChessPosition destination = new ChessPosition(currentRow, i);
                //if there is already a piece at the destination, break
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }

            //all positions in column
            //looking up
            for (int i = currentRow + 1; i <= 8; i++) {

                ChessPosition destination = new ChessPosition(i, myPosition.getColumn());
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }
            //looking down
            for (int i = currentRow - 1; i > 0; i--) {

                ChessPosition destination = new ChessPosition(i, myPosition.getColumn());
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }

        } else if (myPiece.getPieceType() == PieceType.BISHOP) {
            //look up right
            int r = currentRow + 1;
            int c = currentCol + 1;
            while (r <= 8 && c <= 8) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r++;
                c++;
            }
            //look up left
            r = currentRow + 1;
            c = currentCol - 1;
            while (r <= 8 && c > 0) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r++;
                c--;
            }
            //look down left
            r = currentRow - 1;
            c = currentCol - 1;
            while (r > 0 && c > 0) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r--;
                c--;
            }
            //look down right
            r = currentRow - 1;
            c = currentCol + 1;
            while (r > 0 && c <= 8) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r--;
                c++;
            }
        } else if (myPiece.getPieceType() == PieceType.KING) {
            ArrayList<ChessPosition> destinations = new ArrayList<>();

            destinations.add(new ChessPosition(currentRow + 1, currentCol));
            destinations.add(new ChessPosition(currentRow + 1, currentCol + 1));
            destinations.add(new ChessPosition(currentRow, currentCol + 1));
            destinations.add(new ChessPosition(currentRow - 1, currentCol + 1));
            destinations.add(new ChessPosition(currentRow - 1, currentCol));
            destinations.add(new ChessPosition(currentRow - 1, currentCol - 1));
            destinations.add(new ChessPosition(currentRow, currentCol - 1));
            destinations.add(new ChessPosition(currentRow + 1, currentCol - 1));

            for (ChessPosition space : destinations) {
                if (space.inBounds()) {
                    ChessMove move = new ChessMove(myPosition, space, null);
                    possibleMoves.add(move);
                }
            }

        } else if (myPiece.getPieceType() == PieceType.KNIGHT) {
            ArrayList<ChessPosition> destinations = new ArrayList<>();

            destinations.add(new ChessPosition(currentRow + 2, currentCol + 1));
            destinations.add(new ChessPosition(currentRow + 1, currentCol + 2));
            destinations.add(new ChessPosition(currentRow - 2, currentCol + 1));
            destinations.add(new ChessPosition(currentRow - 1, currentCol + 2));
            destinations.add(new ChessPosition(currentRow - 2, currentCol - 1));
            destinations.add(new ChessPosition(currentRow - 1, currentCol - 2));
            destinations.add(new ChessPosition(currentRow + 2, currentCol - 1));
            destinations.add(new ChessPosition(currentRow + 1, currentCol - 2));

            for (ChessPosition space : destinations) {
                if (space.inBounds()) {
                    ChessMove move = new ChessMove(myPosition, space, null);
                    possibleMoves.add(move);
                }
            }
        } else if (myPiece.getPieceType() == PieceType.QUEEN) {
            //copy rook functionality
            //all positions in row
            //looking right
            for (int i = currentCol + 1; i <= 8; i++) {
                ChessPosition destination = new ChessPosition(currentRow, i);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }
            //looking left
            for (int i = currentCol - 1; i > 0; i--) {
                ChessPosition destination = new ChessPosition(currentRow, i);
                //if there is already a piece at the destination, break
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }

            //all positions in column
            //looking up
            for (int i = currentRow + 1; i <= 8; i++) {

                ChessPosition destination = new ChessPosition(i, myPosition.getColumn());
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }
            //looking down
            for (int i = currentRow - 1; i > 0; i--) {

                ChessPosition destination = new ChessPosition(i, myPosition.getColumn());
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
            }

            //copy bishop functionality
            //look up right
            int r = currentRow + 1;
            int c = currentCol + 1;
            while (r <= 8 && c <= 8) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r++;
                c++;
            }
            //look up left
            r = currentRow + 1;
            c = currentCol - 1;
            while (r <= 8 && c > 0) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r++;
                c--;
            }
            //look down left
            r = currentRow - 1;
            c = currentCol - 1;
            while (r > 0 && c > 0) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r--;
                c--;
            }
            //look down right
            r = currentRow - 1;
            c = currentCol + 1;
            while (r > 0 && c <= 8) {
                ChessPosition destination = new ChessPosition(r, c);
                if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                    break;
                } else if (board.getPiece(destination) != null &&
                        board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                    ChessMove move = new ChessMove(myPosition, destination, null);
                    possibleMoves.add(move);
                    break;
                }
                ChessMove move = new ChessMove(myPosition, destination, null);
                possibleMoves.add(move);
                r--;
                c++;
            }

        } else if (myPiece.getPieceType() == PieceType.PAWN) {
            if (myPiece.getTeamColor() == WHITE) {
                ChessPosition forwardOne = new ChessPosition(currentRow + 1, currentCol);
                if (board.getPiece(forwardOne) == null) {
                    if(forwardOne.getRow() == 8){
                        //needs promotion
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.ROOK));
                    } else {
                        ChessMove move = new ChessMove(myPosition, forwardOne, null);
                        possibleMoves.add(move);
                    }

                    ChessPosition forwardTwo = new ChessPosition(currentRow + 2, currentCol);
                    if (currentRow == 2 && board.getPiece(forwardTwo) == null) {
                        ChessMove move2 = new ChessMove(myPosition, forwardTwo, null);
                        possibleMoves.add(move2);
                    }
                }
                ChessPosition slantRight = new ChessPosition(currentRow + 1, currentCol + 1);
                ChessPosition slantLeft = new ChessPosition(currentRow + 1, currentCol - 1);
                if (slantRight.inBounds() && board.getPiece(slantRight) != null && board.getPiece(slantRight).getTeamColor() == BLACK) {
                    if(slantRight.getRow() == 8) {
                        //needs promotion
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.ROOK));
                    } else {
                        ChessMove attackRight = new ChessMove(myPosition, slantRight, null);
                        possibleMoves.add(attackRight);
                    }
                }
                if (slantLeft.inBounds() && board.getPiece(slantLeft) != null && board.getPiece(slantLeft).getTeamColor() == BLACK) {
                    if(slantLeft.getRow() == 8){
                        //needs promotion
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.ROOK));
                    } else {
                        ChessMove attackLeft = new ChessMove(myPosition, slantLeft, null);
                        possibleMoves.add(attackLeft);
                    }
                }

            } else if (myPiece.getTeamColor() == BLACK) {
                ChessPosition forwardOne = new ChessPosition(currentRow - 1, currentCol);
                if (board.getPiece(forwardOne) == null) {
                    if(forwardOne.getRow() == 1){
                        //needs promotion
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, forwardOne, PieceType.ROOK));
                    } else {
                        ChessMove move = new ChessMove(myPosition, forwardOne, null);
                        possibleMoves.add(move);
                    }

                    ChessPosition forwardTwo = new ChessPosition(currentRow - 2, currentCol);
                    if (currentRow == 7 && board.getPiece(forwardTwo) == null) {
                        ChessMove move2 = new ChessMove(myPosition, forwardTwo, null);
                        possibleMoves.add(move2);
                    }
                }
                ChessPosition slantRight = new ChessPosition(currentRow - 1, currentCol + 1);
                ChessPosition slantLeft = new ChessPosition(currentRow - 1, currentCol - 1);
                if (slantRight.inBounds() && board.getPiece(slantRight) != null && board.getPiece(slantRight).getTeamColor() == WHITE) {
                    if(slantRight.getRow() == 1){
                        //needs promotion
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, slantRight, PieceType.ROOK));
                    } else {
                        ChessMove attackRight = new ChessMove(myPosition, slantRight, null);
                        possibleMoves.add(attackRight);
                    }
                }
                if (slantLeft.inBounds() && board.getPiece(slantLeft) != null && board.getPiece(slantLeft).getTeamColor() == WHITE) {
                    if(slantLeft.getRow() == 1){
                        //needs promotion
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.QUEEN));
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.KNIGHT));
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.BISHOP));
                        possibleMoves.add(new ChessMove(myPosition, slantLeft, PieceType.ROOK));
                    } else {
                        ChessMove attackLeft = new ChessMove(myPosition, slantLeft, null);
                        possibleMoves.add(attackLeft);
                    }
                }
            }
        }
        possibleMoves.removeIf(possibleMove -> board.getPiece(possibleMove.getEndPosition()) != null &&
                board.getPiece(possibleMove.getEndPosition()).getTeamColor() == board.getPiece(myPosition).getTeamColor());

//        Iterator<ChessMove> iterator = possibleMoves.iterator();
//        while (iterator.hasNext()) {
//            ChessMove possibleMove = iterator.next();
//            if (board.getPiece(possibleMove.getEndPosition()) != null &&
//                    board.getPiece(possibleMove.getEndPosition()).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
//                iterator.remove();
//            }
//        }

        //        for(ChessMove possibleMove : possibleMoves){
//            if(board.getPiece(possibleMove.getEndPosition()) != null &&
//               board.getPiece(possibleMove.getEndPosition()).getTeamColor() == board.getPiece(myPosition).getTeamColor()){
//                possibleMoves.remove(possibleMove);
//            } //concurrent modification exception!
//        }
        return possibleMoves;
    }
}
