package daos.imples;

import daos.context.DBContext;
import models.Post;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class PostDao extends DBContext<Post> {

    private static final String SELECT_ALL_POSTS_PAGINATED = "SELECT * FROM posts ORDER BY updated_at DESC LIMIT ?, ?";
    private static final String SELECT_TOTAL_POSTS = "SELECT COUNT(*) FROM posts";
    private static final String SELECT_POST_BY_ID = "SELECT * FROM posts WHERE id = ?";

    public PostDao() throws SQLException {
        super();
    }

    @Override
    public List<Post> findAll() {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM posts")) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = mapResultSetToPost(rs);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Post findById(int id) {
        Post post = null;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_POST_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                post = mapResultSetToPost(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Post update(Post model) {
        // Implementation for updating a post
        return null;
    }

    @Override
    public void deleteById(int id) {
        // Implementation for deleting a post
    }

    public List<Post> selectAllPostsPaginated(int offset, int limit) {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_POSTS_PAGINATED)) {
            preparedStatement.setInt(1, offset);
            preparedStatement.setInt(2, limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Post post = mapResultSetToPost(rs);
                posts.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public int getTotalPosts() {
        int totalPosts = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TOTAL_POSTS)) {
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                totalPosts = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalPosts;
    }

    // Helper method to map ResultSet to Post object
    private Post mapResultSetToPost(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String title = rs.getString("title");
        String thumbnail = rs.getString("thumbnail");
        String briefInfo = rs.getString("brief_info");
        String details = rs.getString("details");
        int authorId = rs.getInt("author_id");
        int categoryId = rs.getInt("category_id");
        Timestamp createdAt = rs.getTimestamp("created_at");
        Timestamp updatedAt = rs.getTimestamp("updated_at");
        return new Post(id, title, thumbnail, briefInfo, details, authorId, categoryId, createdAt, updatedAt);
    }

    public List<String> getAllCategories() {
    List<String> categories = new ArrayList<>();
    try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT name FROM categories")) {
        ResultSet rs = preparedStatement.executeQuery();
        while (rs.next()) {
            String category = rs.getString("name");
            categories.add(category);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return categories;
}

}
