package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-04-16 23:05:59")
/** */
public final class QuestionMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.Question> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.util.Date> createdAt = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.util.Date>(this, "createdAt", "createdAt", java.util.Date.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.Question> description = new org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.Question>(this, "description", "description");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Integer> intimacyLevel = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, java.lang.Integer>(this, "intimacyLevel", "intimacyLevel", int.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Question, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.UnindexedAttributeMeta<com.wordpong.api.model.Question, java.util.Locale> locale = new org.slim3.datastore.UnindexedAttributeMeta<com.wordpong.api.model.Question, java.util.Locale>(this, "locale", "locale", java.util.Locale.class);

    /** */
    public final org.slim3.datastore.StringCollectionAttributeMeta<com.wordpong.api.model.Question, java.util.List<java.lang.String>> questions = new org.slim3.datastore.StringCollectionAttributeMeta<com.wordpong.api.model.Question, java.util.List<java.lang.String>>(this, "questions", "questions", java.util.List.class);

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
        java.util.Locale _locale = blobToSerializable((com.google.appengine.api.datastore.Blob) entity.getProperty("locale"));
        model.setLocale(_locale);
        model.setQuestions(toList(java.lang.String.class, entity.getProperty("questions")));
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
        entity.setProperty("description", m.getDescription());
        entity.setProperty("intimacyLevel", m.getIntimacyLevel());
        entity.setUnindexedProperty("locale", serializableToBlob(m.getLocale()));
        entity.setProperty("questions", m.getQuestions());
        entity.setProperty("title", m.getTitle());
        entity.setProperty("updatedAt", m.getUpdatedAt());
        entity.setProperty("user", m.getUser());
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
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getCreatedAt() != null){
            writer.setNextPropertyName("createdAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getCreatedAt());
        }
        if(m.getDescription() != null){
            writer.setNextPropertyName("description");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getDescription());
        }
        writer.setNextPropertyName("intimacyLevel");
        encoder = new org.slim3.datastore.json.Default();
        encoder.encode(writer, m.getIntimacyLevel());
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getLocale() != null){
            writer.setNextPropertyName("locale");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getLocale());
        }
        if(m.getQuestions() != null){
            writer.setNextPropertyName("questions");
            encoder = new org.slim3.datastore.json.Default();
            writer.beginArray();
            for(java.lang.String v : m.getQuestions()){
                encoder.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getTitle() != null){
            writer.setNextPropertyName("title");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getTitle());
        }
        if(m.getUpdatedAt() != null){
            writer.setNextPropertyName("updatedAt");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUpdatedAt());
        }
        if(m.getUser() != null){
            writer.setNextPropertyName("user");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUser());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.setNextPropertyName("visibility");
        encoder = new org.slim3.datastore.json.Default();
        encoder.encode(writer, m.getVisibility());
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.Question jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.Question m = new com.wordpong.api.model.Question();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("createdAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setCreatedAt(decoder.decode(reader, m.getCreatedAt()));
        reader = rootReader.newObjectReader("description");
        decoder = new org.slim3.datastore.json.Default();
        m.setDescription(decoder.decode(reader, m.getDescription()));
        reader = rootReader.newObjectReader("intimacyLevel");
        decoder = new org.slim3.datastore.json.Default();
        m.setIntimacyLevel(decoder.decode(reader, m.getIntimacyLevel()));
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("locale");
        decoder = new org.slim3.datastore.json.Default();
        m.setLocale(decoder.decode(reader, m.getLocale(), java.util.Locale.class));
        reader = rootReader.newObjectReader("questions");
        decoder = new org.slim3.datastore.json.Default();
        {
            java.util.ArrayList<java.lang.String> elements = new java.util.ArrayList<java.lang.String>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("questions");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    java.lang.String v = decoder.decode(reader, (java.lang.String)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setQuestions(elements);
            }
        }
        reader = rootReader.newObjectReader("title");
        decoder = new org.slim3.datastore.json.Default();
        m.setTitle(decoder.decode(reader, m.getTitle()));
        reader = rootReader.newObjectReader("updatedAt");
        decoder = new org.slim3.datastore.json.Default();
        m.setUpdatedAt(decoder.decode(reader, m.getUpdatedAt()));
        reader = rootReader.newObjectReader("user");
        decoder = new org.slim3.datastore.json.Default();
        m.setUser(decoder.decode(reader, m.getUser()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        reader = rootReader.newObjectReader("visibility");
        decoder = new org.slim3.datastore.json.Default();
        m.setVisibility(decoder.decode(reader, m.getVisibility()));
        return m;
    }
}