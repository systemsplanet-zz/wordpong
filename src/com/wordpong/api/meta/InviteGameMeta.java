package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-05-21 22:01:38")
/** */
public final class InviteGameMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.InviteGame> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key> activePlayerKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key>(this, "activePlayerKey", "activePlayerKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key> answersKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key>(this, "answersKey", "answersKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.InviteGame> inviteeDetails = new org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.InviteGame>(this, "inviteeDetails", "inviteeDetails");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key> inviteeKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key>(this, "inviteeKey", "inviteeKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.InviteGame> inviterDetails = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.InviteGame>(this, "inviterDetails", "inviterDetails");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key> inviterKey = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key>(this, "inviterKey", "inviterKey", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.lang.Boolean> ignored = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.lang.Boolean>(this, "ignored", "ignored", boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.InviteGame, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final InviteGameMeta slim3_singleton = new InviteGameMeta();

    /**
     * @return the singleton
     */
    public static InviteGameMeta get() {
       return slim3_singleton;
    }

    /** */
    public InviteGameMeta() {
        super("InviteGame", com.wordpong.api.model.InviteGame.class);
    }

    @Override
    public com.wordpong.api.model.InviteGame entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.InviteGame model = new com.wordpong.api.model.InviteGame();
        model.setActivePlayerKey((com.google.appengine.api.datastore.Key) entity.getProperty("activePlayerKey"));
        model.setAnswersKey((com.google.appengine.api.datastore.Key) entity.getProperty("answersKey"));
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setInviteeDetails((java.lang.String) entity.getProperty("inviteeDetails"));
        model.setInviteeKey((com.google.appengine.api.datastore.Key) entity.getProperty("inviteeKey"));
        model.setInviterDetails((java.lang.String) entity.getProperty("inviterDetails"));
        model.setInviterKey((com.google.appengine.api.datastore.Key) entity.getProperty("inviterKey"));
        model.setIgnored(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("ignored")));
        model.setKey(entity.getKey());
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.InviteGame m = (com.wordpong.api.model.InviteGame) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("activePlayerKey", m.getActivePlayerKey());
        entity.setProperty("answersKey", m.getAnswersKey());
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setProperty("inviteeDetails", m.getInviteeDetails());
        entity.setProperty("inviteeKey", m.getInviteeKey());
        entity.setUnindexedProperty("inviterDetails", m.getInviterDetails());
        entity.setProperty("inviterKey", m.getInviterKey());
        entity.setUnindexedProperty("ignored", m.isIgnored());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.InviteGame m = (com.wordpong.api.model.InviteGame) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.InviteGame m = (com.wordpong.api.model.InviteGame) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.InviteGame m = (com.wordpong.api.model.InviteGame) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.InviteGame m = (com.wordpong.api.model.InviteGame) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        com.wordpong.api.model.InviteGame m = (com.wordpong.api.model.InviteGame) model;
        m.setCreatedAt(slim3_createdAtAttributeListener.prePut(m.getCreatedAt()));
        m.setUpdatedAt(slim3_updatedAtAttributeListener.prePut(m.getUpdatedAt()));
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
        com.wordpong.api.model.InviteGame m = (com.wordpong.api.model.InviteGame) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getActivePlayerKey() != null){
            writer.setNextPropertyName("activePlayerKey");
            encoder0.encode(writer, m.getActivePlayerKey());
        }
        if(m.getAnswersKey() != null){
            writer.setNextPropertyName("answersKey");
            encoder0.encode(writer, m.getAnswersKey());
        }
        if(m.getCreatedAt() != null){
            writer.setNextPropertyName("createdAt");
            encoder0.encode(writer, m.getCreatedAt());
        }
        if(m.getInviteeDetails() != null){
            writer.setNextPropertyName("inviteeDetails");
            encoder0.encode(writer, m.getInviteeDetails());
        }
        if(m.getInviteeKey() != null){
            writer.setNextPropertyName("inviteeKey");
            encoder0.encode(writer, m.getInviteeKey());
        }
        if(m.getInviterDetails() != null){
            writer.setNextPropertyName("inviterDetails");
            encoder0.encode(writer, m.getInviterDetails());
        }
        if(m.getInviterKey() != null){
            writer.setNextPropertyName("inviterKey");
            encoder0.encode(writer, m.getInviterKey());
        }
        writer.setNextPropertyName("ignored");
        encoder0.encode(writer, m.isIgnored());
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getUpdatedAt() != null){
            writer.setNextPropertyName("updatedAt");
            encoder0.encode(writer, m.getUpdatedAt());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.InviteGame jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.InviteGame m = new com.wordpong.api.model.InviteGame();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("activePlayerKey");
        m.setActivePlayerKey(decoder0.decode(reader, m.getActivePlayerKey()));
        reader = rootReader.newObjectReader("answersKey");
        m.setAnswersKey(decoder0.decode(reader, m.getAnswersKey()));
        reader = rootReader.newObjectReader("createdAt");
        m.setCreatedAt(decoder0.decode(reader, m.getCreatedAt()));
        reader = rootReader.newObjectReader("inviteeDetails");
        m.setInviteeDetails(decoder0.decode(reader, m.getInviteeDetails()));
        reader = rootReader.newObjectReader("inviteeKey");
        m.setInviteeKey(decoder0.decode(reader, m.getInviteeKey()));
        reader = rootReader.newObjectReader("inviterDetails");
        m.setInviterDetails(decoder0.decode(reader, m.getInviterDetails()));
        reader = rootReader.newObjectReader("inviterKey");
        m.setInviterKey(decoder0.decode(reader, m.getInviterKey()));
        reader = rootReader.newObjectReader("ignored");
        m.setIgnored(decoder0.decode(reader, m.isIgnored()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("updatedAt");
        m.setUpdatedAt(decoder0.decode(reader, m.getUpdatedAt()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}