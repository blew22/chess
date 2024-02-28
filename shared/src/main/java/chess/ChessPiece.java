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
            lookRight(possibleMoves, board, myPosition);
            lookLeft(possibleMoves, board, myPosition);
            //all positions in column
            lookUp(possibleMoves, board, myPosition);
            lookDown(possibleMoves, board, myPosition);

        } else if (myPiece.getPieceType() == PieceType.BISHOP) {
            lookUpRight(possibleMoves, board, myPosition);
            lookUpLeft(possibleMoves, board, myPosition);
            lookDownLeft(possibleMoves, board, myPosition);
            lookDownRight(possibleMoves, board, myPosition);

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
            lookRight(possibleMoves, board, myPosition);
            lookLeft(possibleMoves, board, myPosition);
            lookUp(possibleMoves, board, myPosition);
            lookDown(possibleMoves, board, myPosition);

            lookUpRight(possibleMoves, board, myPosition);
            lookUpLeft(possibleMoves, board, myPosition);
            lookDownLeft(possibleMoves, board, myPosition);
            lookDownRight(possibleMoves, board, myPosition);

        } else if (myPiece.getPieceType() == PieceType.PAWN) {
            if (myPiece.getTeamColor() == WHITE) {
                ChessPosition forwardOne = new ChessPosition(currentRow + 1, currentCol);
                if (board.getPiece(forwardOne) == null) {
                    if (forwardOne.getRow() == 8) {
                        addPromotionMoves(possibleMoves, myPosition, forwardOne);
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
                    if (slantRight.getRow() == 8) {
                        addPromotionMoves(possibleMoves, myPosition, slantRight);
                    } else {
                        ChessMove attackRight = new ChessMove(myPosition, slantRight, null);
                        possibleMoves.add(attackRight);
                    }
                }
                if (slantLeft.inBounds() && board.getPiece(slantLeft) != null && board.getPiece(slantLeft).getTeamColor() == BLACK) {
                    if (slantLeft.getRow() == 8) {
                        addPromotionMoves(possibleMoves, myPosition, slantLeft);
                    } else {
                        ChessMove attackLeft = new ChessMove(myPosition, slantLeft, null);
                        possibleMoves.add(attackLeft);
                    }
                }

            } else if (myPiece.getTeamColor() == BLACK) {
                ChessPosition forwardOne = new ChessPosition(currentRow - 1, currentCol);
                if (board.getPiece(forwardOne) == null) {
                    if (forwardOne.getRow() == 1) {
                        addPromotionMoves(possibleMoves, myPosition, forwardOne);
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
                    if (slantRight.getRow() == 1) {
                        addPromotionMoves(possibleMoves, myPosition, slantRight);
                    } else {
                        ChessMove attackRight = new ChessMove(myPosition, slantRight, null);
                        possibleMoves.add(attackRight);
                    }
                }
                if (slantLeft.inBounds() && board.getPiece(slantLeft) != null && board.getPiece(slantLeft).getTeamColor() == WHITE) {
                    if (slantLeft.getRow() == 1) {
                        addPromotionMoves(possibleMoves, myPosition, slantLeft);
                    } else {
                        ChessMove attackLeft = new ChessMove(myPosition, slantLeft, null);
                        possibleMoves.add(attackLeft);
                    }
                }
            }
        }
        possibleMoves.removeIf(possibleMove -> board.getPiece(possibleMove.getEndPosition()) != null &&
                board.getPiece(possibleMove.getEndPosition()).getTeamColor() == board.getPiece(myPosition).getTeamColor());

        return possibleMoves;
    }

    private void lookRight(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int currentCol = myPosition.getColumn();
        int currentRow = myPosition.getRow();
        for (int i = currentCol + 1; i <= 8; i++) {
            ChessPosition destination = new ChessPosition(currentRow, i);
            ChessMove move = new ChessMove(myPosition, destination, null);
            if (board.getPiece(destination) != null &&
                    board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                break; //stops loop if it reaches teammate
            } else if (board.getPiece(destination) != null &&
                    board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                possibleMoves.add(move);
                break; //adds space when potentially capturing opponent, then stops loop
            }
            possibleMoves.add(move);
        }
    }

    private void lookLeft(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
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
    }

    private void lookUp(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        for (int i = currentRow + 1; i <= 8; i++) {
            ChessPosition destination = new ChessPosition(i, currentCol);
            ChessMove move = new ChessMove(myPosition, destination, null);
            if (board.getPiece(destination) != null &&
                    board.getPiece(destination).getTeamColor() == board.getPiece(myPosition).getTeamColor()) {
                break;
            } else if (board.getPiece(destination) != null &&
                    board.getPiece(destination).getTeamColor() != board.getPiece(myPosition).getTeamColor()) {
                possibleMoves.add(move);
                break;
            }
            possibleMoves.add(move);
        }
    }

    private void lookDown(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();
        for (int i = currentRow - 1; i > 0; i--) {
            ChessPosition destination = new ChessPosition(i, currentCol);
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
    }

    private void lookUpRight(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int r = myPosition.getRow() + 1;
        int c = myPosition.getColumn() + 1;
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
    }

    private void lookUpLeft(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int r = myPosition.getRow() + 1;
        int c = myPosition.getColumn() - 1;
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
    }

    private void lookDownLeft(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int r = myPosition.getRow() - 1;
        int c = myPosition.getColumn() - 1;
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
    }

    private void lookDownRight(Collection<ChessMove> possibleMoves, ChessBoard board, ChessPosition myPosition) {
        int r = myPosition.getRow() - 1;
        int c = myPosition.getColumn() + 1;
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
    }

    private void addPromotionMoves(Collection<ChessMove> possibleMoves, ChessPosition myPosition, ChessPosition move) {
        possibleMoves.add(new ChessMove(myPosition, move, PieceType.QUEEN));
        possibleMoves.add(new ChessMove(myPosition, move, PieceType.KNIGHT));
        possibleMoves.add(new ChessMove(myPosition, move, PieceType.BISHOP));
        possibleMoves.add(new ChessMove(myPosition, move, PieceType.ROOK));
    }
}