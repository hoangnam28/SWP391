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

        return null;
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

    public List<Post> selectPostsByCategoryPaginated(int categoryId, int offset, int noOfRecords) {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM posts WHERE category_id = ? LIMIT ?, ?")) {
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, offset);
            preparedStatement.setInt(3, noOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String title = rs.getString("title");
                String thumbnail = rs.getString("thumbnail");
                String briefInfo = rs.getString("brief_info");
                String details = rs.getString("details");
                int authorId = rs.getInt("author_id");
                int categoryIdFromDB = rs.getInt("category_id");
                Timestamp createdAt = rs.getTimestamp("created_at");
                Timestamp updatedAt = rs.getTimestamp("updated_at");
                posts.add(new Post(id, title, thumbnail, briefInfo, details, authorId, categoryIdFromDB, createdAt, updatedAt));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public int getTotalPostsByCategory(int categoryId) {
        int total = 0;
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT COUNT(*) FROM posts WHERE category_id = ?")) {
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public List<Post> getLatestPosts(int limit) {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM posts ORDER BY updated_at DESC LIMIT ?")) {
            preparedStatement.setInt(1, limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    public List<Post> searchPostsByTitle(String title) {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM posts WHERE title LIKE ?")) {
            preparedStatement.setString(1, "%" + title + "%");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    
    }
    public List<Post> getHostPosts(int limit) {
        List<Post> posts = new ArrayList<>();
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "SELECT * FROM posts ORDER BY updated_at ASC LIMIT ?")) {
            preparedStatement.setInt(1, limit);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                posts.add(mapResultSetToPost(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }
   

}
