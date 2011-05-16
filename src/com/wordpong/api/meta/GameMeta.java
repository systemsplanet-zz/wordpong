package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-05-15 17:03:29")
/** */
public final class GameMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.Game> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key> answers = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key>(this, "answers", "answers", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, java.lang.Boolean> completed = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, java.lang.Boolean>(this, "completed", "completed", boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, java.lang.Boolean> ignored = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, java.lang.Boolean>(this, "ignored", "ignored", boolean.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key> questions = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key>(this, "questions", "questions", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key> user = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, com.google.appengine.api.datastore.Key>(this, "user", "user", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Game, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final GameMeta slim3_singleton = new GameMeta();

    /**
     * @return the singleton
     */
    public static GameMeta get() {
       return slim3_singleton;
    }

    /** */
    public GameMeta() {
        super("Game", com.wordpong.api.model.Game.class);
    }

    @Override
    public com.wordpong.api.model.Game entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.Game model = new com.wordpong.api.model.Game();
        model.setAnswers((com.google.appengine.api.datastore.Key) entity.getProperty("answers"));
        model.setCompleted(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("completed")));
        model.setIgnored(booleanToPrimitiveBoolean((java.lang.Boolean) entity.getProperty("ignored")));
        model.setKey(entity.getKey());
        model.setQuestions((com.google.appengine.api.datastore.Key) entity.getProperty("questions"));
        model.setUser((com.google.appengine.api.datastore.Key) entity.getProperty("user"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.Game m = (com.wordpong.api.model.Game) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("answers", m.getAnswers());
        entity.setProperty("completed", m.isCompleted());
        entity.setUnindexedProperty("ignored", m.isIgnored());
        entity.setProperty("questions", m.getQuestions());
        entity.setProperty("user", m.getUser());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.Game m = (com.wordpong.api.model.Game) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.Game m = (com.wordpong.api.model.Game) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.Game m = (com.wordpong.api.model.Game) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.Game m = (com.wordpong.api.model.Game) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
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
        com.wordpong.api.model.Game m = (com.wordpong.api.model.Game) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getAnswers() != null){
            writer.setNextPropertyName("answers");
            encoder0.encode(writer, m.getAnswers());
        }
        writer.setNextPropertyName("completed");
        encoder0.encode(writer, m.isCompleted());
        writer.setNextPropertyName("ignored");
        encoder0.encode(writer, m.isIgnored());
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getQuestions() != null){
            writer.setNextPropertyName("questions");
            encoder0.encode(writer, m.getQuestions());
        }
        if(m.getUser() != null){
            writer.setNextPropertyName("user");
            encoder0.encode(writer, m.getUser());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.Game jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.Game m = new com.wordpong.api.model.Game();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("answers");
        m.setAnswers(decoder0.decode(reader, m.getAnswers()));
        reader = rootReader.newObjectReader("completed");
        m.setCompleted(decoder0.decode(reader, m.isCompleted()));
        reader = rootReader.newObjectReader("ignored");
        m.setIgnored(decoder0.decode(reader, m.isIgnored()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("questions");
        m.setQuestions(decoder0.decode(reader, m.getQuestions()));
        reader = rootReader.newObjectReader("user");
        m.setUser(decoder0.decode(reader, m.getUser()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        return m;
    }
}