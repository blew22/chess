package chess;

import java.util.Collection;
import java.util.Map;
import java.util.Vector;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {
    private
    ChessBoard board;
    TeamColor teamTurn;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        return board.getPiece(startPosition).pieceMoves(board, startPosition);
    }

    private void moveHelper (ChessMove move, ChessBoard board){
        board.addPiece(move.getEndPosition(), board.getPiece(move.getStartPosition()));
        board.removePiece(move.getStartPosition());
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {

        moveHelper(move, board);




    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessBoard potentialBoard = board;
        TeamColor opponentColor;
        if (teamColor == TeamColor.WHITE) {opponentColor = TeamColor.BLACK;}
        else {opponentColor = TeamColor.WHITE;}
        ChessPosition kingPosition = potentialBoard.findPiece(ChessPiece.PieceType.KING, teamColor);

        //get map of opposite team piece/position pairs
        Map<ChessPosition, ChessPiece> opponentPieces = potentialBoard.getTeamPieces(opponentColor);
        //for each entry, get its possible moves
        for(Map.Entry<ChessPosition /*key*/, ChessPiece/*value*/> entry : opponentPieces.entrySet()) {
            Collection<ChessMove> possibleMoves = entry.getValue().pieceMoves(potentialBoard, entry.getKey());
            //for each possible move, check if it captures the king
            for(ChessMove possibleMove : possibleMoves){
                if(possibleMove.getEndPosition().equals(kingPosition)){
                    return true;
                }
            }
        };
//        for (ChessPiece piece : opponentPieces) {
//            Collection<ChessMove> possibleMoves = piece.pieceMoves(potentialBoard, potentialBoard.findPiece(piece.getPieceType(), TeamColor.BLACK));
//            //for each possible move, check if it captures the king
//            for(ChessMove possibleMove : possibleMoves){
//                if(possibleMove.getEndPosition() == kingPosition){
//                    return true;
//                }
//            }
//        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return (isInCheck(teamColor) && isInStalemate(teamColor));
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //Returns true if the given team has no legal moves and it is currently that team’s turn.
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }
}
