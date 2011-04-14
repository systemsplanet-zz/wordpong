package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-04-13 23:00:01")
/** */
public final class FriendInviteMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.FriendInvite> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.FriendInvite> inviteeEmail = new org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.FriendInvite>(this, "inviteeEmail", "inviteeEmail");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, com.google.appengine.api.datastore.Key> inviterKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, com.google.appengine.api.datastore.Key>(this, "inviterKey", "inviterKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.FriendInvite, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final FriendInviteMeta slim3_singleton = new FriendInviteMeta();

    /**
     * @return the singleton
     */
    public static FriendInviteMeta get() {
       return slim3_singleton;
    }

    /** */
    public FriendInviteMeta() {
        super("FriendInvite", com.wordpong.api.model.FriendInvite.class);
    }

    @Override
    public com.wordpong.api.model.FriendInvite entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.FriendInvite model = new com.wordpong.api.model.FriendInvite();
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setInviteeEmail((java.lang.String) entity.getProperty("inviteeEmail"));
        model.setInviterKey((com.google.appengine.api.datastore.Key) entity.getProperty("inviterKey"));
        model.setKey(entity.getKey());
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.FriendInvite m = (com.wordpong.api.model.FriendInvite) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setProperty("inviteeEmail", m.getInviteeEmail());
        entity.setUnindexedProperty("inviterKey", m.getInviterKey());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.FriendInvite m = (com.wordpong.api.model.FriendInvite) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.FriendInvite m = (com.wordpong.api.model.FriendInvite) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.FriendInvite m = (com.wordpong.api.model.FriendInvite) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.FriendInvite m = (com.wordpong.api.model.FriendInvite) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        com.wordpong.api.model.FriendInvite m = (com.wordpong.api.model.FriendInvite) model;
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
        com.wordpong.api.model.FriendInvite m = (com.wordpong.api.model.FriendInvite) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getCreatedAt() != null){
            writer.setNextPropertyName("createdAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCreatedAt());
        }
        if(m.getInviteeEmail() != null){
            writer.setNextPropertyName("inviteeEmail");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getInviteeEmail());
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
    protected com.wordpong.api.model.FriendInvite jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.FriendInvite m = new com.wordpong.api.model.FriendInvite();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("createdAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setCreatedAt(decoder.decode(reader, m.getCreatedAt()));
        reader = rootReader.newObjectReader("inviteeEmail");
        decoder = new org.slim3.datastore.json.Default();
        m.setInviteeEmail(decoder.decode(reader, m.getInviteeEmail()));
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