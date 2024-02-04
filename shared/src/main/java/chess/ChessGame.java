package chess;

import java.util.*;

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

    public void changeTeamTurn(){
        if (getTeamTurn() == TeamColor.WHITE){
            setTeamTurn(TeamColor.BLACK);
        } else {
            setTeamTurn(TeamColor.WHITE);
        }
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessGame chessGame = (ChessGame) o;
        return Objects.equals(getBoard(), chessGame.getBoard()) && getTeamTurn() == chessGame.getTeamTurn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBoard(), getTeamTurn());
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        TeamColor team = board.getPiece(startPosition).getTeamColor();
        Collection<ChessMove> possibleMoves = board.getPiece(startPosition).pieceMoves(board, startPosition);
        ArrayList<ChessMove> validMoves = new ArrayList<>();

        for (ChessMove move : possibleMoves) {
            //make move, check, and undo move
            ChessPiece tempPiece = board.getPiece(move.getEndPosition());
            boolean needToUndoCapture = false;
            if(tempPiece != null){
                needToUndoCapture = true;
            }
            moveHelper(move, board);
            if (!isInCheck(team)) {
                validMoves.add(move);
            }
            undoMove(move);
            //make sure to replace any piece that was captured
            if(needToUndoCapture){
                board.addPiece(move.getEndPosition(), tempPiece);
            }
        }
        return validMoves;
    }

    private void undoMove(ChessMove move){
        moveHelper(new ChessMove(move.getEndPosition(), move.getStartPosition(), move.promotionPiece), board);
    }

    private void moveHelper(ChessMove move, ChessBoard board) {
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
//        Collection<ChessMove> validMoves = validMoves(move.getStartPosition());
//        //if move not in valid moves, throw error. else, make move
//        if (validMoves.contains(move)) {
//            moveHelper(move, board);
//            changeTeamTurn();
//        } else {
//            throw new InvalidMoveException();
//        }
        moveHelper(move, board);

    }

    private void tryMove(ChessMove move, ChessBoard differentBoard) {
        moveHelper(move, differentBoard);
    }

    private TeamColor getOppositeTeamColor(TeamColor teamColor){
        TeamColor opponentColor;
        if (teamColor == TeamColor.WHITE) {
            opponentColor = TeamColor.BLACK;
        } else {
            opponentColor = TeamColor.WHITE;
        }
        return opponentColor;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessBoard potentialBoard = board;
        TeamColor opponentColor = getOppositeTeamColor(teamColor);
        ChessPosition kingPosition = potentialBoard.findPiece(ChessPiece.PieceType.KING, teamColor);

        //get map of opposite team piece/position pairs
        Map<ChessPosition, ChessPiece> opponentPieces = potentialBoard.getTeamPieces(opponentColor);
        //for each entry, get its possible moves
        for (Map.Entry<ChessPosition /*key*/, ChessPiece/*value*/> entry : opponentPieces.entrySet()) {
            //use valid moves instead of piece moves??
            Collection<ChessMove> possibleMoves = entry.getValue().pieceMoves(potentialBoard, entry.getKey());
            //for each possible move, check if it captures the king
            for (ChessMove possibleMove : possibleMoves) {
                if (possibleMove.getEndPosition().equals(kingPosition)) {
                    return true;
                }
            }
        };
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
        TeamColor opponentColor = getOppositeTeamColor(teamColor);
        //get map of all pieces on team and their position
        Map<ChessPosition, ChessPiece> teamPieces = board.getTeamPieces(teamColor);
        //for each piece, check valid moves
        for(Map.Entry<ChessPosition/*key*/, ChessPiece/*value*/> entry : teamPieces.entrySet()){
            Collection<ChessMove> legalMoves = validMoves(entry.getKey());
            //if valid moves is not empty, return false
            if(!legalMoves.isEmpty()) return false;
        }
        return true;
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
