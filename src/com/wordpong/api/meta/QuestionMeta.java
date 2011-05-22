package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-05-21 20:46:02")
/** */
public final class QuestionMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.Question> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Question> description = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Question>(this, "description", "description");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Integer> intimacyLevel = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Integer>(this, "intimacyLevel", "intimacyLevel", int.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Question> localeString = new org.slim3.datastore.StringUnindexedAttributeMeta<com.wordpong.api.model.Question>(this, "localeString", "localeString");

    /** */
    public final org.slim3.datastore.StringCollectionUnindexedAttributeMeta<com.wordpong.api.model.Question, java.util.List<java.lang.String>> questions = new org.slim3.datastore.StringCollectionUnindexedAttributeMeta<com.wordpong.api.model.Question, java.util.List<java.lang.String>>(this, "questions", "questions", java.util.List.class);

    /** */
    public final org.slim3.datastore.StringCollectionUnindexedAttributeMeta<com.wordpong.api.model.Question, java.util.Set<java.lang.String>> tags = new org.slim3.datastore.StringCollectionUnindexedAttributeMeta<com.wordpong.api.model.Question, java.util.Set<java.lang.String>>(this, "tags", "tags", java.util.Set.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.Question> title = new org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.Question>(this, "title", "title");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.util.Date> updatedAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.util.Date>(this, "updatedAt", "updatedAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, com.google.appengine.api.datastore.Key> user = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, com.google.appengine.api.datastore.Key>(this, "user", "user", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Integer> visibility = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Integer>(this, "visibility", "visibility", int.class);

    private static final org.slim3.datastore.CreationDate slim3_createdAtAttributeListener = new org.slim3.datastore.CreationDate();

    private static final org.slim3.datastore.ModificationDate slim3_updatedAtAttributeListener = new org.slim3.datastore.ModificationDate();

    private static final QuestionMeta slim3_singleton = new QuestionMeta();

    /**
     * @return the singleton
     */
    public static QuestionMeta get() {
       return slim3_singleton;
    }

    /** */
    public QuestionMeta() {
        super("Question", com.wordpong.api.model.Question.class);
    }

    @Override
    public com.wordpong.api.model.Question entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.Question model = new com.wordpong.api.model.Question();
        model.setCreatedAt((java.util.Date) entity.getProperty("createdAt"));
        model.setDescription((java.lang.String) entity.getProperty("description"));
        model.setIntimacyLevel(longToPrimitiveInt((java.lang.Long) entity.getProperty("intimacyLevel")));
        model.setKey(entity.getKey());
        model.setLocaleString((java.lang.String) entity.getProperty("localeString"));
        model.setQuestions(toList(java.lang.String.class, entity.getProperty("questions")));
        model.setTags(new java.util.HashSet<java.lang.String>(toList(java.lang.String.class, entity.getProperty("tags"))));
        model.setTitle((java.lang.String) entity.getProperty("title"));
        model.setUpdatedAt((java.util.Date) entity.getProperty("updatedAt"));
        model.setUser((com.google.appengine.api.datastore.Key) entity.getProperty("user"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        model.setVisibility(longToPrimitiveInt((java.lang.Long) entity.getProperty("visibility")));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.Question m = (com.wordpong.api.model.Question) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("createdAt", m.getCreatedAt());
        entity.setUnindexedProperty("description", m.getDescription());
        entity.setProperty("intimacyLevel", m.getIntimacyLevel());
        entity.setUnindexedProperty("localeString", m.getLocaleString());
        entity.setUnindexedProperty("questions", m.getQuestions());
        entity.setUnindexedProperty("tags", m.getTags());
        entity.setProperty("title", m.getTitle());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setUnindexedProperty("user", m.getUser());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("visibility", m.getVisibility());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.Question m = (com.wordpong.api.model.Question) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.Question m = (com.wordpong.api.model.Question) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.Question m = (com.wordpong.api.model.Question) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.Question m = (com.wordpong.api.model.Question) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
        com.wordpong.api.model.Question m = (com.wordpong.api.model.Question) model;
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
        com.wordpong.api.model.Question m = (com.wordpong.api.model.Question) model;
        writer.beginObject();
        org.slim3.datastore.json.Default encoder0 = new org.slim3.datastore.json.Default();
        if(m.getCreatedAt() != null){
            writer.setNextPropertyName("createdAt");
            encoder0.encode(writer, m.getCreatedAt());
        }
        if(m.getDescription() != null){
            writer.setNextPropertyName("description");
            encoder0.encode(writer, m.getDescription());
        }
        writer.setNextPropertyName("intimacyLevel");
        encoder0.encode(writer, m.getIntimacyLevel());
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder0.encode(writer, m.getKey());
        }
        if(m.getLocaleString() != null){
            writer.setNextPropertyName("localeString");
            encoder0.encode(writer, m.getLocaleString());
        }
        if(m.getQuestions() != null){
            writer.setNextPropertyName("questions");
            writer.beginArray();
            for(java.lang.String v : m.getQuestions()){
                encoder0.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getTags() != null){
            writer.setNextPropertyName("tags");
            writer.beginArray();
            for(java.lang.String v : m.getTags()){
                encoder0.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getTitle() != null){
            writer.setNextPropertyName("title");
            encoder0.encode(writer, m.getTitle());
        }
        if(m.getUpdatedAt() != null){
            writer.setNextPropertyName("updatedAt");
            encoder0.encode(writer, m.getUpdatedAt());
        }
        if(m.getUser() != null){
            writer.setNextPropertyName("user");
            encoder0.encode(writer, m.getUser());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder0.encode(writer, m.getVersion());
        }
        writer.setNextPropertyName("visibility");
        encoder0.encode(writer, m.getVisibility());
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.Question jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.Question m = new com.wordpong.api.model.Question();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.Default decoder0 = new org.slim3.datastore.json.Default();
        reader = rootReader.newObjectReader("createdAt");
        m.setCreatedAt(decoder0.decode(reader, m.getCreatedAt()));
        reader = rootReader.newObjectReader("description");
        m.setDescription(decoder0.decode(reader, m.getDescription()));
        reader = rootReader.newObjectReader("intimacyLevel");
        m.setIntimacyLevel(decoder0.decode(reader, m.getIntimacyLevel()));
        reader = rootReader.newObjectReader("key");
        m.setKey(decoder0.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("localeString");
        m.setLocaleString(decoder0.decode(reader, m.getLocaleString()));
        reader = rootReader.newObjectReader("questions");
        {
            java.util.ArrayList<java.lang.String> elements = new java.util.ArrayList<java.lang.String>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("questions");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    java.lang.String v = decoder0.decode(reader, (java.lang.String)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setQuestions(elements);
            }
        }
        reader = rootReader.newObjectReader("tags");
        {
            java.util.HashSet<java.lang.String> elements = new java.util.HashSet<java.lang.String>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("tags");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    java.lang.String v = decoder0.decode(reader, (java.lang.String)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setTags(elements);
            }
        }
        reader = rootReader.newObjectReader("title");
        m.setTitle(decoder0.decode(reader, m.getTitle()));
        reader = rootReader.newObjectReader("updatedAt");
        m.setUpdatedAt(decoder0.decode(reader, m.getUpdatedAt()));
        reader = rootReader.newObjectReader("user");
        m.setUser(decoder0.decode(reader, m.getUser()));
        reader = rootReader.newObjectReader("version");
        m.setVersion(decoder0.decode(reader, m.getVersion()));
        reader = rootReader.newObjectReader("visibility");
        m.setVisibility(decoder0.decode(reader, m.getVisibility()));
        return m;
    }
}