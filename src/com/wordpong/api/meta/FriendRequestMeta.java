package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-04-16 18:39:04")
/** */
public final class FriendRequestMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.FriendRequest> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreUnindexedAttributeMeta<com.wordpong.api.model.FriendRequest, java.util.Date> invitedAt = new org.slim3.datastore.CoreUnindexedAttributeMeta<com.wordpong.api.model.FriendRequest, java.util.Date>(this, "invitedAt", "invitedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, com.google.appengine.api.datastore.Key> inviterKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, com.google.appengine.api.datastore.Key>(this, "inviterKey", "inviterKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendRequest, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final FriendRequestMeta slim3_singleton = new FriendRequestMeta();

    /**
     * @return the singleton
     */
    public static FriendRequestMeta get() {
       return slim3_singleton;
    }

    /** */
    public FriendRequestMeta() {
        super("FriendRequest", com.wordpong.api.model.FriendRequest.class);
    }

    @Override
    public com.wordpong.api.model.FriendRequest entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.FriendRequest model = new com.wordpong.api.model.FriendRequest();
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setInvitedAt((java.util.Date) entity.getProperty("invitedAt"));
        model.setInviterKey((com.google.appengine.api.datastore.Key) entity.getProperty("inviterKey"));
        model.setKey(entity.getKey());
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.FriendRequest m = (com.wordpong.api.model.FriendRequest) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setUnindexedProperty("invitedAt", m.getInvitedAt());
        entity.setUnindexedProperty("inviterKey", m.getInviterKey());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.FriendRequest m = (com.wordpong.api.model.FriendRequest) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.FriendRequest m = (com.wordpong.api.model.FriendRequest) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.FriendRequest m = (com.wordpong.api.model.FriendRequest) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.FriendRequest m = (com.wordpong.api.model.FriendRequest) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        com.wordpong.api.model.FriendRequest m = (com.wordpong.api.model.FriendRequest) model;
        m.setCreatedAt(slim3_createdAtAttributeListener.prePut(m.getCreatedAt()));
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        com.wordpong.api.model.FriendRequest m = (com.wordpong.api.model.FriendRequest) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getCreatedAt() != null){
            writer.setNextPropertyName("createdAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCreatedAt());
        }
        if(m.getInvitedAt() != null){
            writer.setNextPropertyName("invitedAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getInvitedAt());
        }
        if(m.getInviterKey() != null){
            writer.setNextPropertyName("inviterKey");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getInviterKey());
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.FriendRequest jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.FriendRequest m = new com.wordpong.api.model.FriendRequest();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("createdAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setCreatedAt(decoder.decode(reader, m.getCreatedAt()));
        reader = rootReader.newObjectReader("invitedAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setInvitedAt(decoder.decode(reader, m.getInvitedAt()));
        reader = rootReader.newObjectReader("inviterKey");
        decoder = new org.slim3.datastore.json.Default();
        m.setInviterKey(decoder.decode(reader, m.getInviterKey()));
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}